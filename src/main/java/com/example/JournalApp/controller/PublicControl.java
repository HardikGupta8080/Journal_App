package com.example.JournalApp.controller;

import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserDetailServiceImpl;
import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.JournalApp.utilis.JwtUtils;

@RequestMapping("/public")
@RestController
public class PublicControl {
    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryService journalEntryService;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private UserDetailServiceImpl userDetailService;
@Autowired
private JwtUtils jwtUtils;
    @PostMapping("/Signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.savenewUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestBody User user) {
    try {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        UserDetails userDetails = userDetailService.loadUserByUsername(user.getUsername());
        String token=jwtUtils.generateToken(userDetails.getUsername());
        return new ResponseEntity<>(token,HttpStatus.OK);
    } catch (AuthenticationException e) {
        return new ResponseEntity<>(e,HttpStatus.UNAUTHORIZED);

   }
    }
}
