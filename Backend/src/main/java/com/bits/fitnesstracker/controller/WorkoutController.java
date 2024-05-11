package com.bits.fitnesstracker.controller;

import com.bits.fitnesstracker.model.WorkoutPlan;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.service.WorkoutService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("workout")
@SecurityRequirement(name = "jwtAuth")
@CrossOrigin(origins = "http://localhost:52000", methods = {RequestMethod.GET})
@Tag(name = "WorkoutController", description = "This is a protected API. Send Authorization Header\n" +
        "Example:\n" +
        "HeaderKey: \"Authorization\"\n" +
        "HeaderValue: \"Bearer TOKEN_VALUE\"")
public class WorkoutController {

    @Autowired
    WorkoutService workoutService;

    @GetMapping("/workoutPlans")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout Plan fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Exception while fetching Workout Plan")})
    public ResponseEntity<Response<List<WorkoutPlan>>> getWorkoutPlans(@RequestParam @Parameter(description = "Gender of the user", example = "female") String gender,
                                                                      @RequestParam @Parameter(description = "Weight of the user", example = "55") int weight) {
        return ResponseEntity.ok(workoutService.getWorkoutPlan(gender,weight));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}