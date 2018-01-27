package com.happiness.swipereclyerview.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.happiness.swipereclyerview.DialogFragment;
import com.happiness.swipereclyerview.ItemListFragment;
import com.happiness.swipereclyerview.R;
import com.happiness.swipereclyerview.SCommonItemDecoration;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.Section;
import java.util.ArrayList;
import java.util.List;
import rx.Emitter;
import rx.Observable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends ItemListFragment<MyGroup<MyModel>> {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  @BindView(android.R.id.list) RecyclerView recyclerView;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public GroupFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment GroupFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static GroupFragment newInstance(String param1, String param2) {
    GroupFragment fragment = new GroupFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    setEmptyText(R.string.empty);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);


    setupRecylerView();
  }

  @Override protected int getErrorMessage() {
    return R.string.error;
  }

  @Override protected Observable<List<MyGroup<MyModel>>> loadData(boolean forceRefresh) {
    return Observable.create(listEmitter -> {
      ArrayList<MyGroup<MyModel>> list = new ArrayList<>();
      for (int i = 0; i < 5; i++) {
        list.add(new MyGroup<>("group" + i,null));
        for (int j = 0; j < 10; j++) {
          list.add(new MyGroup<>("group" + i, new MyModel("name:" + (i * 100) + j,
              "sex:" + (((i * 100) + j) % 2 == 0 ? "男" : "女"))));
        }
      }
      listEmitter.onNext(list);
    }, Emitter.BackpressureMode.DROP);
  }

  @Override protected void onDataLoaded(List<Item> newItems) {

    super.onDataLoaded(newItems);
  }

  @Override protected Item createItem(MyGroup<MyModel> item) {
    if (item.getModel()!=null){
      return new MyModelItem(item.getModel());
    }else {
      return new GroupItem(item);
    }
  }

  private void setupRecylerView(){

    RecyclerView recyclerView = getRecyclerView();
    //int verticalSpace = dip2px(this.getActivity(), 1);
    //int horizontalSpace = dip2px(this.getActivity(), 25);
    SparseArray<SCommonItemDecoration.ItemDecorationProps> propMap = new SparseArray<>();
    //SCommonItemDecoration.ItemDecorationProps prop1 =
    //    new SCommonItemDecoration.ItemDecorationProps(horizontalSpace, verticalSpace, false, false);
    //propMap.put(R.layout.my_group_item, prop1);

    //verticalSpace = dip2px(this.getActivity(), 5);
    //horizontalSpace = dip2px(this.getActivity(), 5);
    SCommonItemDecoration.ItemDecorationProps prop2 =
        new SCommonItemDecoration.ItemDecorationProps(2, 2, true, false);
    propMap.put(R.layout.my_model_item, prop2);
    recyclerView.addItemDecoration(new SCommonItemDecoration(propMap));

  }

}
