package com.bits.fitnesstracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("workouts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Workout {
    @Id
    private int id;
    private int weight;
    private String gender;
    private List<WorkoutPlan> workoutPlans;
}
