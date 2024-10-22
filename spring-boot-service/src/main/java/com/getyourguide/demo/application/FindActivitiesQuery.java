package com.getyourguide.demo.application;

public class FindActivitiesQuery {
  private final String searchTerm;
  private final int page;
  private final int size;

  public FindActivitiesQuery(String searchTerm, int page, int size) {
    this.searchTerm = searchTerm;
    this.page = page;
    this.size = size;
  }

  public String getSearchTerm() {
    return searchTerm;
  }

  public int getPage() {
    return page;
  }

  public int getSize() {
    return size;
  }
}
