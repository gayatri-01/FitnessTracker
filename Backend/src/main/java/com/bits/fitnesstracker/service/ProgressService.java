package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.Activity;
import com.bits.fitnesstracker.model.Progress;
import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.repository.ActivityRepository;
import com.bits.fitnesstracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProgressService {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    public Response<Progress> getProgress(String username) {
        List<Activity> activities = activityRepository.findLastWeekActivities(username);
        User user = userRepository.findUserByUsername(username);

        int stepsCompleted = 0;
        int stepsGoal = user.getStepsGoal();
        double caloriesBurnt = 0;
        double caloriesGoal = user.getCaloriesGoal();
        Map<String, Integer> stepsMap = new TreeMap<>();
        Map<String, Double> caloriesMap = new TreeMap<>();

        for (Activity activity : activities) {
            stepsMap.put(activity.getDate(), stepsMap.getOrDefault(activity.getDate(), 0) + activity.getSteps());
            caloriesMap.put(activity.getDate(), caloriesMap.getOrDefault(activity.getDate(), 0.0) + activity.getCaloriesBurnt());
        }

        for (Map.Entry<String, Integer> entry : stepsMap.entrySet()) {
            int normalised = (int) (((double) entry.getValue() / stepsGoal) * 100);
            entry.setValue(Math.min(normalised, 100));
            stepsCompleted += entry.getValue();
        }

        for (Map.Entry<String, Double> entry : caloriesMap.entrySet()) {
            double normalised = ((entry.getValue() / caloriesGoal) * 100);
            entry.setValue(Math.min(normalised, 100));
            caloriesBurnt += entry.getValue();
        }

        int normalizedStepsCompleted = (int) ((double) stepsCompleted / 7);
        double normalizedCaloriesBurnt = caloriesBurnt / 7;

        Progress progress = new Progress();
        progress.setLabelsBarChartSteps(stepsMap.keySet().stream().toList());
        progress.setStepsBarChart(stepsMap.values().stream().toList());
        progress.setLabelsBarChartCalories(caloriesMap.keySet().stream().toList());
        progress.setCaloriesBarChart(caloriesMap.values().stream().toList());
        progress.setStepsDoughNutChart(List.of(normalizedStepsCompleted,100-normalizedStepsCompleted));
        progress.setCaloriesDoughNutChart(List.of(normalizedCaloriesBurnt,100-normalizedCaloriesBurnt));

        return Response.<Progress>builder().status(HttpStatus.OK.value()).message("Progress fetched successfully").results(progress).build();

    }
}
