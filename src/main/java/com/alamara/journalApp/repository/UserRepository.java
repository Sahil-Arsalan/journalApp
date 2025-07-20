package com.alamara.journalApp.repository;

import com.alamara.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String name);
}
