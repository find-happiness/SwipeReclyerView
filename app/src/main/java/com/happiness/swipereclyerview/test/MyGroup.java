package com.happiness.swipereclyerview.test;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MyGroup<E> {
  String group;
  List<E> modelList;

  public MyGroup(String group, List<E> modelList) {
    this.group = group;
    this.modelList = modelList;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public List<E> getModelList() {
    return modelList;
  }

  public void setModelList(List<E> modelList) {
    this.modelList = modelList;
  }
}
