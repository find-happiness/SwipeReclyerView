package com.happiness.swipereclyerview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.OnItemLongClickListener;
import com.xwray.groupie.Section;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ItemListFragment<E> extends DialogFragment
    implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener, OnItemLongClickListener {

  @BindView(R.id.swipe_layout) SwipeRefreshLayout swipeLayout;
  @BindView(android.R.id.list) RecyclerView recyclerView;
  @BindView(android.R.id.empty) TextView emptyView;
  @BindView(R.id.pb_loading) ProgressBar progressBar;

  /**
   * List items.
   */
  protected List<Item> items = new ArrayList<>();

  /**
   * Is the list currently shown?.
   */
  protected boolean listShown;

  /**
   * The adapter used by the {@link RecyclerView} to display {@link com.xwray.groupie.Group}:s
   * from Groupie.
   */
  private GroupAdapter adapter = new GroupAdapter();

  /**
   * The {@link Section} containing headers, footers and the items.
   */
  private Section mainSection = new Section();

  /**
   * Is the fragment currently loading data.
   */
  protected boolean isLoading;

  Subscription subscription;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter.add(mainSection);
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (!items.isEmpty()) {
      setListShown(true, false);
    } else {
      refresh();
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_item_list, container, false);
  }

  @Override public void onRefresh() {
    forceRefresh();
  }

  /**
   * Detach from list view.
   */
  @Override public void onDestroyView() {
    listShown = false;
    emptyView = null;
    progressBar = null;
    recyclerView = null;

    super.onDestroyView();
  }

  @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    swipeLayout.setOnRefreshListener(this);
    //swipeLayout.setColorSchemeResources(
    //    R.color.pager_title_background_top_start,
    //    R.color.pager_title_background_end,
    //    R.color.text_link,
    //    R.color.pager_title_background_end);

    configureList(getRecyclerView());
  }

  /**
   * Configure list after view has been created.
   */
  protected void configureList(RecyclerView recyclerView) {

    adapter.setOnItemClickListener(this);
    adapter.setOnItemLongClickListener(this);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);
  }

  /**
   * Force a refresh of the items displayed ignoring any cached items.
   */
  protected void forceRefresh() {
    items.clear();
    refresh(true);
  }

  public void refresh() {
    items.clear();
    refresh(false);
  }

  /**
   * Refresh the fragment's list.
   */
  protected void refresh(boolean force) {
    if (!isUsable() || isLoading) {
      return;
    }

    if (subscription != null && !subscription.isUnsubscribed()) {
      subscription.unsubscribe();
    }

    isLoading = true;

    subscription = loadData(force).subscribeOn(Schedulers.io())
        .flatMap(items -> Observable.from(items).map(this::createItem).toList())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onDataLoaded, this::onDataLoadError);

    //dataLoadDisposable = loadData(force)
    //    .flatMap(items -> Observable.fromIterable(items)
    //        .map(this::createItem)
    //        .toList())
    //    .subscribeOn(Schedulers.io())
    //    .observeOn(AndroidSchedulers.mainThread())
    //    .as(AutoDisposeUtils.bindToLifecycle(this))
    //    .subscribe(this::onDataLoaded, this::onDataLoadError);
  }

  /**
   * Get error message to display for exception.
   *
   * @return string resource id
   */
  protected abstract int getErrorMessage();

  ///**
  // * Get layout to display
  // * @return layout 资源
  // */
  //protected abstract int getLayout();

  /**
   * Load data async via a Single.
   *
   * @param forceRefresh If the loading is forced.
   * @return Single to subscribe to.
   */
  protected abstract Observable<List<E>> loadData(boolean forceRefresh);

  /**
   * Create a {@link Item} from the data item.
   *
   * @param item The data item to create an item with.
   * @return A new item.
   */
  protected abstract Item createItem(E item);

  /**
   * Called when the data has loaded.
   *
   * @param newItems The items added to the list.
   */
  protected void onDataLoaded(List<Item> newItems) {
    if (!isUsable()) {
      return;
    }

    isLoading = false;
    swipeLayout.setRefreshing(false);

    items.addAll(newItems);
    mainSection.update(items);

    showList();
  }

  protected void onDataLoadError(Throwable throwable) {
    if (!isUsable()) {
      return;
    }

    isLoading = false;
    swipeLayout.setRefreshing(false);

    if (throwable != null) {
      showError(throwable, getErrorMessage());
      showList();
    }
  }

  /**
   * Set the list to be shown.
   */
  protected void showList() {
    setListShown(true, isResumed());
  }

  /**
   * Show exception in a {@link Toast}.
   */
  protected void showError(final Throwable e, final int defaultMessage) {
    ToastUtils.show(getActivity(), e, defaultMessage);
  }

  /**
   * Refresh the list with the progress bar showing.
   */
  protected void refreshWithProgress() {
    items.clear();
    setListShown(false);
    refresh();
  }

  /**
   * Get {@link RecyclerView}.
   *
   * @return recyclerView
   */
  public RecyclerView getRecyclerView() {
    return recyclerView;
  }

  /**
   * Get main {@link Section} for adding footers and headers.
   *
   * @return mainSection
   */
  public Section getMainSection() {
    return mainSection;
  }

  /**
   * Get the {@link android.support.v7.widget.RecyclerView.LayoutManager} for
   * the {@link RecyclerView}
   *
   * @return recyclerView
   */
  public LinearLayoutManager getLayoutManager() {
    if (recyclerView != null) {
      return (LinearLayoutManager) recyclerView.getLayoutManager();
    } else {
      return null;
    }
  }

  /**
   * Get list adapter.
   *
   * @return list adapter
   */
  public GroupAdapter getListAdapter() {
    return adapter;
  }

  /**
   * Notify the underlying adapter that the data set has changed.
   *
   * @return this fragment
   */
  protected ItemListFragment notifyDataSetChanged() {
    getListAdapter().notifyDataSetChanged();
    return this;
  }

  private ItemListFragment fadeIn(final View view, final boolean animate) {
    if (view != null) {
      if (animate) {
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
      } else {
        view.clearAnimation();
      }
    }
    return this;
  }

  private ItemListFragment show(final View view) {
    view.setVisibility(View.VISIBLE);
    return this;
  }

  private ItemListFragment hide(final View view) {
    view.setVisibility(View.GONE);
    return this;
  }

  /**
   * Set list shown or progress bar show.
   *
   * @return this fragment
   */
  public ItemListFragment setListShown(final boolean shown) {
    return setListShown(shown, true);
  }

  /**
   * Set list shown or progress bar show.
   *
   * @return this fragment
   */
  public ItemListFragment setListShown(final boolean shown, final boolean animate) {
    if (!isUsable()) {
      return this;
    }

    if (shown == listShown) {
      if (shown) {
        // List has already been shown so hide/show the empty view with
        // no fade effect
        if (items.isEmpty()) {
          hide(recyclerView).show(emptyView);
        } else {
          hide(emptyView).show(recyclerView);
        }
      }
      return this;
    }

    listShown = shown;

    if (shown) {
      if (!items.isEmpty()) {
        hide(progressBar).hide(emptyView).fadeIn(recyclerView, animate).show(recyclerView);
      } else {
        hide(progressBar).hide(recyclerView).fadeIn(emptyView, animate).show(emptyView);
      }
    } else {
      hide(recyclerView).hide(emptyView).fadeIn(progressBar, animate).show(progressBar);
    }

    return this;
  }

  /**
   * Set empty text on list fragment.
   *
   * @return this fragment
   */
  protected ItemListFragment setEmptyText(final String message) {
    if (emptyView != null) {
      emptyView.setText(message);
    }
    return this;
  }

  /**
   * Set empty text on list fragment.
   *
   * @return this fragment
   */
  protected ItemListFragment setEmptyText(final int resId) {
    if (emptyView != null) {
      emptyView.setText(resId);
    }
    return this;
  }

  /**
   * Callback when a list view item is clicked.
   */
  @Override public void onItemClick(@NonNull Item item, @NonNull View view) {

  }

  /**
   * Callback when a list view item is clicked and held.
   *
   * @return true if the callback consumed the long click, false otherwise
   */
  @Override public boolean onItemLongClick(@NonNull Item item, @NonNull View view) {
    return false;
  }
}
