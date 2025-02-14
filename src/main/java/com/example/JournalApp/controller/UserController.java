package com.example.JournalApp.controller;

import com.example.JournalApp.Repository.JournalEntryRepository;
import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;

    @PutMapping
    public ResponseEntity<?> UpdateUser( @RequestBody User user) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User old = userService.findByUserName(username);
        if (old!=null) {
            user.setId(old.getId());

            if (user.getUsername().isEmpty() || user.getUsername() == null) {
                user.setUsername(old.getUsername());
            }
            if (user.getPassword().isEmpty() || user.getPassword() == null) {
                user.setPassword(old.getPassword());
            }
            userService.saveUser(user);
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No such user exist", HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        if(userService.findByUserName(username)!=null){
            userService.deleteUser(userService.findByUserName(username).getId());
            return new ResponseEntity<>("Deleted",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
