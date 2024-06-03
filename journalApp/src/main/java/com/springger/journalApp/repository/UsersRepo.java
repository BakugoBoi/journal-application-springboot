package com.springger.journalApp.repository;

import com.springger.journalApp.entity.JournalEntryEntity;
import com.springger.journalApp.entity.UsersEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepo extends MongoRepository<UsersEntity, ObjectId> {

    UsersEntity findByUsername(String username);

    void deleteByUsername(String username);
}
