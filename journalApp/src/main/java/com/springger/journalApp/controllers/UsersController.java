package com.springger.journalApp.controllers;

import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.repository.UsersRepo;
import com.springger.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepo usersRepo;

    @GetMapping
    public List<UsersEntity> getAll(){
        return usersService.getAll();
    }

    @PutMapping
    public ResponseEntity<?> updateData(@RequestBody UsersEntity users){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UsersEntity userInDb = usersService.findByUsername(userName);
        userInDb.setUsername(users.getUsername());
        userInDb.setPassword(users.getPassword());
        usersService.saveNewUser(userInDb);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteEntry(@RequestBody UsersEntity users){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        usersRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
