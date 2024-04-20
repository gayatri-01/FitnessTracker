package com.bits.fitnesstracker.repository;

import com.bits.fitnesstracker.model.User;
import com.bits.fitnesstracker.model.Workout;
import com.bits.fitnesstracker.model.WorkoutPlan;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class WorkoutRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<WorkoutPlan> findWorkoutPlans(String gender, int weight) {
        Workout workout = mongoTemplate.findOne(Query.query(Criteria.where("weight").is(weight).and("gender").is(gender)), Workout.class);
        return workout!=null ? workout.getWorkoutPlans() : Collections.EMPTY_LIST;
    }

    public List<WorkoutPlan> findDefaultWorkoutPlan() {
        Workout workout = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(1)), Workout.class);
        assert workout != null;
        return workout.getWorkoutPlans();
    }
}
