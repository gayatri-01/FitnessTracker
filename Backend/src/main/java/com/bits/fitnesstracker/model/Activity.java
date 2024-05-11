package com.bits.fitnesstracker.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Activity {
    @Id
    private ObjectId id;
    private String username;
    private String date;
    private int duration;
    private int steps;
    private double caloriesBurnt;
    private Date createdTimestamp;
}
