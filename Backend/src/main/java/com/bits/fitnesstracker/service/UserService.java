package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public User getUserProfile(String username) {
        return userRepository.findUserByUsername(username);
    }

    public String updateUserProfile(String username, Map<String, Object> changedFields) {
        userRepository.updateUser(username,changedFields);
        return "User Profile Updated Successfully";
    }
}
