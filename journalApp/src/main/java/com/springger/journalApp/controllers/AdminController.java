package com.springger.journalApp.controllers;

import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<UsersEntity> all = usersService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createAdmin(@RequestBody UsersEntity users){
        usersService.saveAdmin(users);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
