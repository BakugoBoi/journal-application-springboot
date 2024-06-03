package com.springger.journalApp.repository;

import com.springger.journalApp.entity.JournalEntryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntryEntity, ObjectId> {

}
