package com.springger.journalApp.entity;

import com.springger.journalApp.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UsersRepo usersRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntity usersEntity = usersRepo.findByUsername(username);
        if (usersEntity!=null){
            return User.builder().username(usersEntity.getUsername())
                    .password(usersEntity.getPassword()).roles(usersEntity.getRoles().toArray(new String[0])).
                    build();
        }
        throw new UsernameNotFoundException("Username not found!");
    }
}
