package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.WorkoutPlan;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.repository.UserRepository;
import com.bits.fitnesstracker.repository.WorkoutRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    public Response<List<WorkoutPlan>> getWorkoutPlan(String gender, int weight) {
        List<WorkoutPlan> workoutPlans = workoutRepository.findWorkoutPlans(gender,weight);
        if (workoutPlans != null && !workoutPlans.isEmpty()) {
            return Response.<List<WorkoutPlan>>builder().status(HttpStatus.OK.value()).message("Workout Plans fetched successfully").results(workoutPlans).build();
        } else {
            //Fallback to default if not found on basis of gender & weight
            workoutPlans = workoutRepository.findDefaultWorkoutPlan();
            return Response.<List<WorkoutPlan>>builder().status(HttpStatus.OK.value()).message("Workout Plans fetched successfully").results(workoutPlans).build();
        }
    }
}
