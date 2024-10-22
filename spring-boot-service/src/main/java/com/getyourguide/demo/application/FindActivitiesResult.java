package com.getyourguide.demo.application;

import com.getyourguide.demo.domain.Activity;
import java.util.List;

public class FindActivitiesResult {
  private final List<Activity> activities;
  private final long total;
  private final int totalPages;
  private final String next;
  private final String prev;

  public FindActivitiesResult(List<Activity> activities, long total, int totalPages, String next, String prev) {
    this.activities = activities;
    this.total = total;
    this.totalPages = totalPages;
    this.next = next;
    this.prev = prev;
  }

  public List<Activity> getActivities() {
    return activities;
  }

  public long getTotal() {
    return total;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public String getNext() {
    return next;
  }

  public String getPrev() {
    return prev;
  }
}
