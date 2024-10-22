package com.getyourguide.demo.application;

import com.getyourguide.demo.domain.Activity;
import java.util.List;

public class FindActivitiesResult {
  final List<Activity> activities;
  final long total;
  final int totalPages;
  final String next;
  final String prev;

  public FindActivitiesResult(List<Activity> activities, long total, int totalPages, String next, String prev) {
    this.activities = activities;
    this.total = total;
    this.totalPages = totalPages;
    this.next = next;
    this.prev = prev;
  }
}
