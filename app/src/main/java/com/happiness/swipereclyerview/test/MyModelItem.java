package com.happiness.swipereclyerview.test;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.happiness.swipereclyerview.BaseDataItem;
import com.happiness.swipereclyerview.BaseViewHolder;
import com.happiness.swipereclyerview.R;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MyModelItem extends BaseDataItem<MyModel, MyModelItem.ViewHolder> {

  public MyModelItem(MyModel dataItem) {
    super(dataItem, dataItem.hashCode());
  }

  @Override public int getLayout() {
    return R.layout.my_model_item;
  }

  @NonNull @Override public ViewHolder createViewHolder(@NonNull View itemView) {
    return new ViewHolder(itemView);
  }

  @Override public void bind(@NonNull ViewHolder viewHolder, int position) {

    viewHolder.name.setText(getData().getName());
    viewHolder.sex.setText(getData().getSex());
  }

  class ViewHolder extends BaseViewHolder {
    @BindView(R.id.name) TextView name;
    @BindView(R.id.sex) TextView sex;

    public ViewHolder(@NonNull View rootView) {
      super(rootView);
    }
  }
}
