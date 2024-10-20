package com.getyourguide.demo;

import com.getyourguide.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public ResponseEntity<PaginatedResponse<Activity>> getActivities(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size) {
        try {
            PaginatedResponse<Activity> response = activityService.searchActivities(query, page, size);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}