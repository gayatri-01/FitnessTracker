package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.request.LoginRequest;
import com.bits.fitnesstracker.model.request.RegisterRequest;
import com.bits.fitnesstracker.model.response.AuthResponse;
import com.bits.fitnesstracker.repository.UserRepository;
import com.bits.fitnesstracker.security.JwtTokenUtil;
import com.bits.fitnesstracker.security.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthResponse registerUser(RegisterRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(userRequest.getPassword()));
        user.setRole("USER");
        user.setUsername(userRequest.getUsername());
        userRepository.saveUser(user);
        UserDetails userDetails = userDetailsService.createUserDetails(userRequest.getUsername(), user.getPassword());
        String token = jwtTokenUtil.generateToken(userDetails);
        return AuthResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Account created successfully")
                .token(token)
                .build();

    }

    public AuthResponse loginUser(LoginRequest userRequest) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword()));
        if (auth.isAuthenticated()) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userRequest.getUsername());
            String token = jwtTokenUtil.generateToken(userDetails);
            return AuthResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Logged In Successfully")
                    .token(token)
                    .build();
        } else {
            return AuthResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.value())
                    .message("Invalid Credentials")
                    .build();
        }
    }
}
