package com.bits.fitnesstracker.repository;

import com.bits.fitnesstracker.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Service
public class ActivityRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public List<Activity> findLatestActivities(String username) {
        return mongoTemplate.find(Query.query(Criteria.where("username").is(username)).with(Sort.by(Sort.Direction.DESC, "createdTimestamp")).limit(5), Activity.class);
    }

    public List<Activity> findLastWeekActivities(String username) {
        // Get the end date
        LocalDate endDate = LocalDate.now().plusDays(1);

        // Calculate the start date of the last week (1 week ago from today)
        LocalDate startDate = endDate.minusDays(6);

        Date startDate2 = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Date endDate2 = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Criteria criteria1 = Criteria.where("username").is(username);
        Criteria criteria2 = Criteria.where("createdTimestamp").lte(endDate2);
        Criteria criteria3 = Criteria.where("createdTimestamp").gte(startDate2);
        return mongoTemplate.find(Query.query(new Criteria().andOperator(criteria1,criteria2,criteria3)), Activity.class);
    }

    public void saveActivity(Activity activity) {
        mongoTemplate.insert(activity);
    }
}
