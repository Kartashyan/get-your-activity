package com.getyourguide.demo;
import com.getyourguide.demo.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public ResponseEntity<List<Activity>> getActivities(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        try {
            List<Activity> activities = activityService.searchActivities(query, page, size);
            return ResponseEntity.ok(activities);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
