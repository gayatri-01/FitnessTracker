package com.bits.fitnesstracker.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Progress {
    List<String> labelsBarChartSteps;
    List<Integer> stepsBarChart;

    List<String> labelsBarChartCalories;
    List<Double> caloriesBarChart;

    List<String> labelsDoughNutChart = List.of("Completed", "Remaining");
    List<Integer> stepsDoughNutChart;
    List<Double> caloriesDoughNutChart;
}
