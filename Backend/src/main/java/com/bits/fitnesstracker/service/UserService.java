package com.bits.fitnesstracker.service;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.response.Response;
import com.bits.fitnesstracker.repository.UserRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;


    public Response<User> getUserProfile(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user != null) {
            return Response.<User>builder().status(HttpStatus.OK.value()).message("Profile fetched successfully").results(user).build();
        } else {
            return Response.<User>builder().status(HttpStatus.NO_CONTENT.value()).message("User Not Found").build();
        }
    }

    public Response<String> updateUserProfile(String username, Map<String, Object> changedFields) {
        UpdateResult updateResult = userRepository.updateUser(username, changedFields);
        if (updateResult.getMatchedCount() < 1) {
            return Response.<String>builder().message("No matches found to update").status(HttpStatus.NO_CONTENT.value()).build();
        } else if (updateResult.getModifiedCount() < 1) {
            return Response.<String>builder().message("No modifications required").status(HttpStatus.NO_CONTENT.value()).build();
        } else {
            return Response.<String>builder().message("User Profile Updated Successfully").status(HttpStatus.OK.value()).build();
        }

    }
}
