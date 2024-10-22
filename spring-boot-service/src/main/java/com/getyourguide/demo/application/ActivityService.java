package com.getyourguide.demo.application;

import com.getyourguide.demo.domain.Activity;
import com.getyourguide.demo.domain.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService implements ActivityQuery {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public FindActivitiesResult find(FindActivitiesQuery query) throws IOException {
        List<Activity> activities = activityRepository.getAllActivities();
        List<Activity> filteredActivities = activities.stream()
                .filter(activity -> activity.getTitle() != null && activity.getTitle().toLowerCase().contains(query.getSearchTerm().toLowerCase()))
                .collect(Collectors.toList());

        long total = filteredActivities.size();
        int totalPages = (int) Math.ceil((double) total / query.getSize());
        int fromIndex = Math.min((query.getPage() - 1) * query.getSize(), filteredActivities.size());
        int toIndex = Math.min(fromIndex + query.getSize(), filteredActivities.size());
        List<Activity> paginatedActivities = filteredActivities.subList(fromIndex, toIndex);

        String next = query.getPage() < totalPages ? "/api/activities?page=" + (query.getPage() + 1) : null;
        String prev = query.getPage() > 1 ? "/api/activities?page=" + (query.getPage() - 1) : null;

        return new FindActivitiesResult(paginatedActivities, total, totalPages, next, prev);
    }
}