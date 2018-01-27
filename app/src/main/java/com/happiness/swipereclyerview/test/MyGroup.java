package com.happiness.swipereclyerview.test;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MyGroup<E> {
  String group;
  E model;

  public MyGroup(String group, E modelList) {
    this.group = group;
    this.model = modelList;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public E getModel() {
    return model;
  }

  public void setModel(E model) {
    this.model = model;
  }
}
