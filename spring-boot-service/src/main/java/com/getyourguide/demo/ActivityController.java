package com.getyourguide.demo;

import com.getyourguide.demo.application.ActivityQuery;
import com.getyourguide.demo.application.FindActivitiesQuery;
import com.getyourguide.demo.application.FindActivitiesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class ActivityController {

    private final ActivityQuery activityQuery;

    @Autowired
    public ActivityController(ActivityQuery activityQuery) {
        this.activityQuery = activityQuery;
    }

    @GetMapping("/activities")
    public ResponseEntity<FindActivitiesResult> getActivities(
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "15") int size) {
        try {
            FindActivitiesQuery findActivitiesQuery = new FindActivitiesQuery(query, page, size);
            FindActivitiesResult result = activityQuery.find(findActivitiesQuery);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}