package com.getyourguide.demo.domain;

import java.io.IOException;
import java.util.List;

public interface ActivityRepository {
    List<Activity> getAllActivities() throws IOException;
}