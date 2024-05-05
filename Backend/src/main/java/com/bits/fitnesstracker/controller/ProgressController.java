package com.bits.fitnesstracker.controller;

import com.bits.fitnesstracker.model.Progress;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.service.ProgressService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/progress")
@SecurityRequirement(name = "jwtAuth")
@CrossOrigin(origins = "http://localhost:52000", methods = {RequestMethod.GET})
@Tag(name = "ProgressController", description = "This is a protected API. Send Authorization Header\n" +
        "Example:\n" +
        "HeaderKey: \"Authorization\"\n" +
        "HeaderValue: \"Bearer TOKEN_VALUE\"")
public class ProgressController {

    @Autowired
    ProgressService progressService;

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Progress fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Exception while fetching Progress")})
    public ResponseEntity<Response<Progress>> getProgress(@RequestParam @Parameter(example = "gayatri01") String username) {
        return ResponseEntity.ok(progressService.getProgress(username));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}