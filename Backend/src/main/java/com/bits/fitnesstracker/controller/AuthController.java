package com.bits.fitnesstracker.controller;

import com.bits.fitnesstracker.model.request.LoginRequest;
import com.bits.fitnesstracker.model.request.RegisterRequest;
import com.bits.fitnesstracker.model.response.AuthResponse;
import com.bits.fitnesstracker.service.AuthService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:63342", methods = {RequestMethod.GET, RequestMethod.POST})
@Tag(name = "AuthController", description = "JWT Tokens are used for Security\n" +
        "You will get token in response from the register/login APIs.\n" +
        "Store it in local or session storage to be used later for User Operations")
public class AuthController {
    
    @Autowired
    AuthService authService;


    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account created successfully"),
            @ApiResponse(responseCode = "500", description = "Exception while creating account")})
    public ResponseEntity<AuthResponse> registerUser(@org.springframework.web.bind.annotation.RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "example1", value = "{\n" +
                    "    \"name\": \"Gayatri\",\n" +
                    "    \"username\": \"gayatri01\",\n" +
                    "    \"password\": \"test\",\n" +
                    "    \"email\": \"gayatri@gmail.com\"\n" +
                    "}")
    }) )RegisterRequest userRequest) {
        AuthResponse authResponse = authService.registerUser(userRequest);
        return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged In Successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid Credentials"),
            @ApiResponse(responseCode = "500", description = "Exception while logging in")})
    public ResponseEntity<AuthResponse> loginUser(@org.springframework.web.bind.annotation.RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(name = "example1", value = "{\n" +
                    "    \"username\": \"gayatri01\",\n" +
                    "    \"password\": \"test\"\n" +
                    "}"),
    }) ) LoginRequest userRequest) {
        AuthResponse authResponse =  authService.loginUser(userRequest);
        return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        AuthResponse authResponse = AuthResponse.builder()
                .status(exception instanceof BadCredentialsException ? HttpStatus.UNAUTHORIZED.value() : HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(authResponse.getStatus()).body(authResponse);
    }

}