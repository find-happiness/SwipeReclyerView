package com.happiness.swipereclyerview;

import android.support.annotation.NonNull;
import android.view.View;
import com.xwray.groupie.Item;

public abstract class BaseDataItem<T, V extends BaseViewHolder> extends Item<V> {

  private T data;

  public BaseDataItem(T dataItem, long id) {
    super(id);
    this.data = dataItem;
  }

  public T getData() {
    return data;
  }

  @Override public boolean equals(Object obj) {
    if (obj instanceof BaseDataItem) {
      return getData().equals(((BaseDataItem) obj).getData());
    }
    return super.equals(obj);
  }

  @NonNull @Override public abstract V createViewHolder(@NonNull View itemView);
}
