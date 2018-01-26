package com.happiness.swipereclyerview;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class HeadFootFragment<E> extends PagedItemFragment<E> {

  private ResourceHeadFootIndicator headFootIndicator;

  @Override protected void configureList(RecyclerView recyclerView) {
    super.configureList(recyclerView);

    headFootIndicator = new ResourceHeadFootIndicator(getLoadingMessage());

    headFootIndicator.setSection(getMainSection());

    headFootIndicator.setHead(true);
  }
}
