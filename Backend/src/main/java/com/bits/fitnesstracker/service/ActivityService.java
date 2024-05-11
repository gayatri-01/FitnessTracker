package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.Activity;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    public Response<List<Activity>> getLatestActivities(String username) {
        List<Activity> activities = activityRepository.findLatestActivities(username);
        return Response.<List<Activity>>builder().status(HttpStatus.OK.value()).message("Activities fetched successfully").results(activities).build();
    }

    public Response<String> saveActivity(Map<String, Object> activityMap) {
        Activity activity = new Activity();
        activity.setUsername(activityMap.get("username").toString());
        activity.setDate(activityMap.get("date").toString());
        activity.setSteps((Integer) activityMap.get("steps"));
        activity.setDuration((Integer) activityMap.get("duration"));
        try {
            activity.setCaloriesBurnt((Double) activityMap.get("caloriesBurnt"));
        } catch (ClassCastException e) {
            activity.setCaloriesBurnt(((Integer) activityMap.get("caloriesBurnt")).doubleValue());
        }
        activity.setCreatedTimestamp(Date.from(Instant.now()));
        activityRepository.saveActivity(activity);
        return Response.<String>builder().status(HttpStatus.OK.value()).message("Activity saved successfully").build();
    }
}
