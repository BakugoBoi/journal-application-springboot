package com.springger.journalApp.controllers;

import com.springger.journalApp.entity.JournalEntryEntity;
import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.service.JournalEntryService;
import com.springger.journalApp.service.UsersService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalControllerV2 {

    @Autowired
    JournalEntryService journalEntryService;

    @Autowired
    UsersService usersService;

    @GetMapping
    public ResponseEntity<?> getEntryByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UsersEntity user = usersService.findByUsername(userName);
        List<JournalEntryEntity>  all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntryEntity> createEntry(@RequestBody JournalEntryEntity myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UsersEntity user = usersService.findByUsername(userName);
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntryEntity> getEntrybyId(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UsersEntity byUsername = usersService.findByUsername(userName);
        List<JournalEntryEntity> collect = byUsername.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if (!collect.isEmpty()){
            Optional<JournalEntryEntity> journalEntryEntity = journalEntryService.getEntryById(myId);
            if(journalEntryEntity.isPresent()){
                return new ResponseEntity<>(journalEntryEntity.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntrybyId(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteEntryById(myId, userName);
        if (removed)
        {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntryEntity newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UsersEntity byUsername = usersService.findByUsername(userName);
        List<JournalEntryEntity> collect = byUsername.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntryEntity> journalEntryEntity = journalEntryService.getEntryById(id);
            if (journalEntryEntity.isPresent()) {
                JournalEntryEntity old = journalEntryEntity.get();
                old.setTitle(newEntry.getTitle()!=null && newEntry.getTitle() !=""? newEntry.getTitle() : old.getTitle() );
                old.setContent(newEntry.getContent()!=null && newEntry.getContent() != ""? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
