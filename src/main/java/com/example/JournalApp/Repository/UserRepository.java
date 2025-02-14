package com.example.JournalApp.Repository;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface UserRepository extends MongoRepository<User,ObjectId>{
    //Optional<User> findByUserName(String username);
    User findByUsername(String username);
}
