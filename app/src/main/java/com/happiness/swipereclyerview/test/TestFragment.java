package com.happiness.swipereclyerview.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.happiness.swipereclyerview.HeadFootFragment;
import com.happiness.swipereclyerview.PagedItemFragment;
import com.happiness.swipereclyerview.R;
import com.xwray.groupie.Item;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends PagedItemFragment<MyModel> {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;

  public TestFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment TestFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static TestFragment newInstance(String param1, String param2) {
    TestFragment fragment = new TestFragment();
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

  @Override protected int getErrorMessage() {
    return R.string.error;
  }

  @Override protected Item createItem(MyModel item) {
    return new MyModelItem(item);
  }

  @Override protected int getLoadingMessage() {
    return R.string.loading;
  }

  @Override protected Observable<HttpResult<MyModel>> loadData(int loadPage) {
    return Observable.create((Action1<Emitter<HttpResult<MyModel>>>) listEmitter -> {

      ArrayList<MyModel> list = new ArrayList<>();

      if (loadPage < 5) {
        for (int i = 0; i < 3; i++) {
          MyModel item = new MyModel("名字：" + (loadPage *100 + i), i % 2 == 0 ? "男" : "女");
          list.add(item);
        }
        listEmitter.onNext(new HttpResult<MyModel>(1, "success", list, loadPage + 1, loadPage + 2));
      } else {
        listEmitter.onNext(new HttpResult<MyModel>(1, "success", list, loadPage + 1, 0));
      }
    }, Emitter.BackpressureMode.DROP).delay(1, TimeUnit.SECONDS);
  }
}
