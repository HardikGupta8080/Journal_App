package com.example.JournalApp.Repository;

import com.example.JournalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.cdi.MongoRepositoryBean;
import org.springframework.stereotype.Repository;
@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
