package com.getyourguide.demo.application;

public class FindActivitiesQuery {
  public final String searchTerm;
  public final int page;
  public final int size;

  public FindActivitiesQuery(String searchTerm, int page, int size) {
      this.searchTerm = searchTerm;
      this.page = page;
      this.size = size;
  }
}
