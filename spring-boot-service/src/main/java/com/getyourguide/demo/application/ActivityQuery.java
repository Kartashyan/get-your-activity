package com.getyourguide.demo.application;

import java.io.IOException;

public interface ActivityQuery {
  FindActivitiesResult find(FindActivitiesQuery query) throws IOException;
}
