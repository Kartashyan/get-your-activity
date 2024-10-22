package com.getyourguide.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.*;
import com.getyourguide.demo.domain.Activity;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    public List<Activity> getAllActivities() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        var suppliersInputStream = new ClassPathResource("static/suppliers.json").getInputStream();
        List<Supplier> suppliers = objectMapper.readValue(suppliersInputStream, new TypeReference<List<Supplier>>() {});
        Map<Long, Supplier> supplierMap = suppliers.stream().collect(Collectors.toMap(Supplier::getId, supplier -> supplier));

        var activitiesInputStream = new ClassPathResource("static/activities.json").getInputStream();
        List<Activity> activities = objectMapper.readValue(activitiesInputStream, new TypeReference<List<Activity>>() {});

        for (Activity activity : activities) {
            Supplier supplier = supplierMap.get(activity.getSupplier().getId());
            activity.setSupplier(supplier);
        }

        return activities;
    }

    private int calculateTotalPages(long totalItems, int size) {
        return (int) Math.ceil((double) totalItems / size);
    }

    public PaginatedResponse<Activity> searchActivities(String query, int page, int size) throws IOException {
        List<Activity> activities = getAllActivities();
        List<Activity> filteredActivities = activities.stream()
                .filter(activity -> activity.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());

        long total = filteredActivities.size();
        int totalPages = calculateTotalPages(total, size);

        List<Activity> paginatedActivities = filteredActivities.stream()
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());

        String next = page < totalPages ? "/api/activities?query=" + query + "&page=" + (page + 1) + "&size=" + size : null;
        String prev = page > 1 ? "/api/activities?query=" + query + "&page=" + (page - 1) + "&size=" + size : null;

        return new PaginatedResponse<>(next, prev, paginatedActivities, total, totalPages, size);
    }
}