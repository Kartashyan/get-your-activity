package com.getyourguide.demo;

import com.getyourguide.demo.application.ActivityService;
import com.getyourguide.demo.application.FindActivitiesQuery;
import com.getyourguide.demo.application.FindActivitiesResult;
import com.getyourguide.demo.domain.Activity;
import com.getyourguide.demo.domain.ActivityRepository;
import com.getyourguide.demo.domain.Supplier;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ActivityServiceTest {

    @Test
    public void testFindActivities() throws IOException {
        ActivityRepository activityRepository = Mockito.mock(ActivityRepository.class);
        ActivityService activityService = new ActivityService(activityRepository);

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("John Doe");

        Activity activity1 = new Activity();
        activity1.setTitle("Test Activity One");
        activity1.setSupplier(supplier);

        Activity activity2 = new Activity();
        activity2.setTitle("Test Activity Two");
        activity2.setSupplier(supplier);

        List<Activity> activities = Arrays.asList(activity1, activity2);

        when(activityRepository.getAllActivities()).thenReturn(activities);

        FindActivitiesQuery query = new FindActivitiesQuery("One", 1, 10);
        FindActivitiesResult result = activityService.find(query);

        assertEquals(1, result.getActivities().size());
        assertEquals("Test Activity One", result.getActivities().get(0).getTitle());
        assertEquals("John Doe", result.getActivities().get(0).getSupplier().getName());
        assertEquals(1, result.getTotal());
        assertEquals(1, result.getTotalPages());
    }
}
