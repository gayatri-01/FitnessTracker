package com.bits.fitnesstracker.controller;

import com.bits.fitnesstracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String,String> userRequest) {
        Map<String, Object> responseMap = authService.loginUser(userRequest);
        return ResponseEntity.status(Integer.parseInt(responseMap.get("status").toString())).body(responseMap);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String,String> userRequest) {
        Map<String, Object> responseMap = authService.registerUser(userRequest);
        return ResponseEntity.status(Integer.parseInt(responseMap.get("status").toString())).body(responseMap);       
    }
}