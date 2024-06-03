package com.springger.journalApp.service;

import com.springger.journalApp.entity.UsersEntity;
import com.springger.journalApp.repository.UsersRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UsersRepo usersRepo;

    public void saveEntry(UsersEntity user){
        usersRepo.save(user);
    }

    public void saveNewUser(UsersEntity user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        usersRepo.save(user);
    }

    public List<UsersEntity> getAll(){
       return usersRepo.findAll();
    }

    public Optional<UsersEntity> getEntryById(ObjectId id){
        return usersRepo.findById(id);
    }

    public void deleteEntryById(ObjectId id)
    {
        usersRepo.deleteById(id);
    }

    public void updateEntry(ObjectId id,UsersEntity user){
        usersRepo.save(user);
    }
    public UsersEntity findByUsername(String username){
        return usersRepo.findByUsername(username);
    }

    public void saveAdmin(UsersEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        usersRepo.save(user);
    }
}
