package com.springger.journalApp.controllers;

import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UsersService usersService;

    @PostMapping
    public void newEntry(@RequestBody UsersEntity users){
        usersService.saveNewUser(users);
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "Ok";
    }
}
