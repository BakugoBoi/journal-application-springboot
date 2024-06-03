package com.springger.journalApp.service;

import com.springger.journalApp.entity.JournalEntryEntity;
import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UsersService usersService;

    @Transactional
    public void saveEntry(JournalEntryEntity journalEntryEntity, String username) {
        try {

            UsersEntity user = usersService.findByUsername(username);
            journalEntryEntity.setDate(LocalDateTime.now());
            JournalEntryEntity saved = journalEntryRepo.save(journalEntryEntity);
            user.getJournalEntries().add(saved);
            //user.setUsername(null);
            usersService.saveEntry(user);

        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving entry!");
        }
    }

    public void saveEntry(JournalEntryEntity journalEntryEntity) {
        journalEntryRepo.save(journalEntryEntity);
    }

    public List<JournalEntryEntity> getAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntryEntity> getEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String username) {
        boolean removed=false;
        try {
            UsersEntity user = usersService.findByUsername(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed)
            {
                usersService.saveEntry(user);
                journalEntryRepo.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry");
        }
        return removed;
    }

    public void updateEntry(ObjectId id, JournalEntryEntity journalEntryEntity) {
        journalEntryRepo.save(journalEntryEntity);
    }

    public List<JournalEntryEntity> findByUsername(String userName){
        return (List<JournalEntryEntity>) usersService.findByUsername(userName);
    }
}
