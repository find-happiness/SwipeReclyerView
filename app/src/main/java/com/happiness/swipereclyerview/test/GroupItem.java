package com.happiness.swipereclyerview.test;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.happiness.swipereclyerview.BaseDataItem;
import com.happiness.swipereclyerview.BaseViewHolder;
import com.happiness.swipereclyerview.R;

/**
 * Created by Happiness on 2018/1/27.
 */

public class GroupItem extends BaseDataItem<MyGroup<MyModel>, GroupItem.ViewHolder> {

  public GroupItem(MyGroup<MyModel> dataItem) {
    super(dataItem, dataItem.hashCode());
  }

  @Override public int getLayout() {
    return R.layout.my_group_item;
  }

  @NonNull @Override public GroupItem.ViewHolder createViewHolder(@NonNull View itemView) {
    return new GroupItem.ViewHolder(itemView);
  }

  @Override public void bind(@NonNull GroupItem.ViewHolder viewHolder, int position) {

    viewHolder.name.setText(getData().getGroup());
  }

  class ViewHolder extends BaseViewHolder {
    @BindView(R.id.name) TextView name;

    public ViewHolder(@NonNull View rootView) {
      super(rootView);
    }
  }
}