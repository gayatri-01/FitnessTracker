package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.repository.UserRepository;
import com.bits.fitnesstracker.security.JwtTokenUtil;
import com.bits.fitnesstracker.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserRepository userRepository;

    public Map<String, Object> registerUser(Map<String,String> userRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        User user = new User();
        user.setName(userRequest.get("name"));
        user.setEmail(userRequest.get("email"));
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.get("password")));
        user.setRole("USER");
        user.setUsername(userRequest.get("username"));
        try{
            userRepository.saveUser(user);
            UserDetails userDetails = userDetailsService.createUserDetails(userRequest.get("username"), user.getPassword());
            String token = jwtTokenUtil.generateToken(userDetails);
            responseMap.put("message", "Account created successfully");
            responseMap.put("token", token);
            responseMap.put("status", HttpStatus.OK.value());
        } catch(Exception exception){
            responseMap.put("message", exception.getMessage());
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseMap;
    }

    public Map<String, Object> loginUser(Map<String, String> userRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.get("username"), userRequest.get("password")));
            if (auth.isAuthenticated()) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.get("username"));
                String token = jwtTokenUtil.generateToken(userDetails);
                responseMap.put("message", "Logged In Successfully");
                responseMap.put("token", token);
                responseMap.put("status", HttpStatus.OK.value());
            } else {
                responseMap.put("message", "Invalid Credentials");
                responseMap.put("status", HttpStatus.UNAUTHORIZED.value());
            }
        } catch (DisabledException e) {
            responseMap.put("message", "User is disabled");
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        } catch (BadCredentialsException e) {
            responseMap.put("message", "Invalid Credentials");
            responseMap.put("status", HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            responseMap.put("message", "Something went wrong");
            responseMap.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return responseMap;
    }
}
