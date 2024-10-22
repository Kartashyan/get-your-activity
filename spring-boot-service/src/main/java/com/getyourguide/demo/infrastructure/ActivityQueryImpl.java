package com.getyourguide.demo.infrastructure;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.application.ActivityQuery;
import com.getyourguide.demo.application.FindActivitiesQuery;
import com.getyourguide.demo.application.FindActivitiesResult;
import com.getyourguide.demo.domain.Activity;
import com.getyourguide.demo.domain.Supplier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ActivityQueryImpl implements ActivityQuery {

    @Override
    public FindActivitiesResult find(FindActivitiesQuery query) throws IOException {
        List<Activity> activities = getAllActivities();
        List<Activity> filteredActivities = activities.stream()
                .filter(activity -> activity.getTitle().toLowerCase().contains(query.getSearchTerm().toLowerCase()))
                .collect(Collectors.toList());

        long total = filteredActivities.size();
        int totalPages = (int) Math.ceil((double) total / query.getSize());

        List<Activity> paginatedActivities = filteredActivities.stream()
                .skip((long) (query.getPage() - 1) * query.getSize())
                .limit(query.getSize())
                .collect(Collectors.toList());

        String next = query.getPage() < totalPages
                ? "/api/activities?query=" + query.getSearchTerm() + "&page=" + (query.getPage() + 1) + "&size=" + query.getSize()
                : null;

        String prev = query.getPage() > 1
                ? "/api/activities?query=" + query.getSearchTerm() + "&page=" + (query.getPage() - 1) + "&size=" + query.getSize()
                : null;

        return new FindActivitiesResult(paginatedActivities, total, totalPages, next, prev);
    }

    private List<Activity> getAllActivities() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        var suppliersInputStream = new ClassPathResource("static/suppliers.json").getInputStream();
        List<Supplier> suppliers = objectMapper.readValue(suppliersInputStream, new TypeReference<List<Supplier>>() {});
        Map<Long, Supplier> supplierMap = suppliers.stream()
                .collect(Collectors.toMap(Supplier::getId, supplier -> supplier));

        var activitiesInputStream = new ClassPathResource("static/activities.json").getInputStream();
        List<Activity> activities = objectMapper.readValue(activitiesInputStream, new TypeReference<List<Activity>>() {});

        for (Activity activity : activities) {
            Supplier supplier = supplierMap.get(activity.getSupplier().getId());
            activity.setSupplier(supplier);
        }

        return activities;
    }
}
