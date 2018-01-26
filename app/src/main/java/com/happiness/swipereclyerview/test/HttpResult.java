package com.happiness.swipereclyerview.test;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */

public class HttpResult<E> {
  int code;
  String msg;
  List<E> items;
  int currentPage;
  int nextPage;

  public HttpResult(int code, String msg, List<E> items, int currentPage, int nextPage) {
    this.code = code;
    this.msg = msg;
    this.items = items;
    this.currentPage = currentPage;
    this.nextPage = nextPage;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public List<E> getItems() {
    return items;
  }

  public void setItems(List<E> items) {
    this.items = items;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getNextPage() {
    return nextPage;
  }

  public void setNextPage(int nextPage) {
    this.nextPage = nextPage;
  }
}
