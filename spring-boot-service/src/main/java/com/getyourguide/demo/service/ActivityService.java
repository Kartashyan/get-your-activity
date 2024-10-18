package com.getyourguide.demo.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourguide.demo.Activity;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    public List<Activity> getAllActivities() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        var fileInputStream = new ClassPathResource("static/activities.json").getInputStream();
        return objectMapper.readValue(fileInputStream, new TypeReference<List<Activity>>() {});
    }

    public List<Activity> searchActivities(String query, int page, int size) throws IOException {
        List<Activity> activities = getAllActivities();
        return activities.stream()
                .filter(activity -> activity.getTitle().toLowerCase().contains(query.toLowerCase()))
                .skip((page - 1) * size)
                .limit(size)
                .collect(Collectors.toList());
    }
}