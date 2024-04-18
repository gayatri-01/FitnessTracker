package com.bits.fitnesstracker.repository;

import com.bits.fitnesstracker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


import java.util.Map;

@Service
public class UserRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    public User findUserByUsername(String username){
        return mongoTemplate.findOne(Query.query(Criteria.where("_id").is(username)), User.class);
    }

    public void updateUser(String username, Map<String, Object> changedFields) {
        Query query = new Query(Criteria.where("_id").is(username));
        Update update = new Update();
        // Set only the fields that are present in the changedFields map
        for (Map.Entry<String, Object> entry : changedFields.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        mongoTemplate.updateFirst(query, update, User.class);
    }

    public void saveUser(User user) {
        mongoTemplate.insert(user);
    }
}
