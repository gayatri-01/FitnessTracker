package com.bits.fitnesstracker.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @Id
    private String username;
    private String name;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String role;
    private int stepsGoal;
    private double caloriesGoal;
}
