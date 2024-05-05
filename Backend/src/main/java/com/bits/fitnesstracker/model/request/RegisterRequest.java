package com.bits.fitnesstracker.model.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class RegisterRequest {
    String username;
    String name;
    String email;
    String password;
    int stepsGoal;
    double caloriesGoal;
}
