package com.bits.fitnesstracker.controller;


import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/user")
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "UserController", description = "This is a protected API. Send Authorization Header\n" +
        "Example:\n" +
        "HeaderKey: \"Authorization\"\n" +
        "HeaderValue: \"Bearer TOKEN_VALUE\"")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{username}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile fetched successfully"),
            @ApiResponse(responseCode = "204", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Exception while fetching profile")})
    public ResponseEntity<Response<User>> getUserProfile(@PathVariable  @Parameter(description = "username of the user", example = "gayatri01") String username) {
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    @PutMapping("/{username}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
            @ApiResponse(responseCode = "204", description = "No matches found to update or No modifications required"),
            @ApiResponse(responseCode = "500", description = "Exception while updating profile")})
    public ResponseEntity<Response<String>> updateUserProfile(@PathVariable String username, @org.springframework.web.bind.annotation.RequestBody @RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "example1", value = "{\"email\": \"new_email@gmail.com\"}"),
            @ExampleObject(name = "example2", value = "{\n" +
                    "    \"email\": \"new_email@gmail.com\",\n" +
                    "    \"password\": \"newpassword\"\n" +
                    "}")
    }) )Map<String, Object> changedFields) {
        return ResponseEntity.ok(userService.updateUserProfile(username, changedFields));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}