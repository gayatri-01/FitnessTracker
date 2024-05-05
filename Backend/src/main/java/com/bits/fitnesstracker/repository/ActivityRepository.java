package com.bits.fitnesstracker.repository;

import com.bits.fitnesstracker.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Activity> findLatestActivities(String username) {
        return mongoTemplate.find(Query.query(Criteria.where("username").is(username)).with(Sort.by(Sort.Direction.DESC, "date")).limit(5), Activity.class);
    }

    public List<Activity> findLastWeekActivities(String username) {
        // Get the current date
        LocalDate endDate = LocalDate.now();

        // Calculate the start date of the last week (1 week ago from today)
        LocalDate startDate = endDate.minusDays(6);

        Criteria criteria1 = Criteria.where("username").is(username);
        Criteria criteria2 = Criteria.where("date").lte(endDate.toString());
        Criteria criteria3 = Criteria.where("date").gte(startDate.toString());
        return mongoTemplate.find(Query.query(new Criteria().andOperator(criteria1,criteria2,criteria3)), Activity.class);
    }

    public void saveActivity(Activity activity) {
        mongoTemplate.insert(activity);
    }
}
