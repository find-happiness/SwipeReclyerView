package com.happiness.swipereclyerview.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.happiness.swipereclyerview.DialogFragment;
import com.happiness.swipereclyerview.ItemListFragment;
import com.happiness.swipereclyerview.R;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends DialogFragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

  List<MyGroup<MyModel>> groupList = new ArrayList<>();
  @BindView(android.R.id.list) RecyclerView recyclerView;
  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
  private GroupAdapter adapter = new GroupAdapter();
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

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_group,null,false);
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initData();
    initView();
  }

  private void initData() {

    for (int i = 0; i < 5; i++) {
      String groupName = "group:" + i;

      List<MyModel> modelList = new ArrayList<>();
      for (int j = 0; j < 10; j++) {
        modelList.add(new MyModel("name" + j, "sex sddd"));
      }

      groupList.add(new MyGroup<>(groupName, modelList));
    }
  }

  private void initView() {
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);

    //Section section = new Section();
    //section.setHeader(new HeaderItem());
    //section.addAll(bodyItems);
    //groupAdapter.add(section);

  }
}
