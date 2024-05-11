package com.bits.fitnesstracker.controller;

import com.bits.fitnesstracker.model.Activity;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.service.ActivityService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/activity")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "ActivityController", description = "This is a protected API. Send Authorization Header\n" +
        "Example:\n" +
        "HeaderKey: \"Authorization\"\n" +
        "HeaderValue: \"Bearer TOKEN_VALUE\"")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity Plans fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Exception while fetching Activity Plans")})
    public ResponseEntity<Response<List<Activity>>> getActivities(@RequestParam @Parameter(example = "gayatri01") String username){
        return ResponseEntity.ok(activityService.getLatestActivities(username));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activity saved successfully"),
            @ApiResponse(responseCode = "500", description = "Exception while saving activity")})
    public ResponseEntity<Response<String>> saveActivity(@org.springframework.web.bind.annotation.RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "example1", value = "{\"email\": \"new_email@gmail.com\"}")
    }) ) Map<String, Object> activity) {
        return ResponseEntity.ok(activityService.saveActivity(activity));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}