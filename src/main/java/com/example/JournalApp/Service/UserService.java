package com.example.JournalApp.Service;

import com.example.JournalApp.Repository.UserRepository;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;

@Component
public class UserService {
    @Autowired
    public UserRepository userRepository;
    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();
    public void saveUser(User user){
        userRepository.save(user);
    }

    public void savenewUser(User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER"));
        userRepository.save(user);
    }

    public void deleteUser(ObjectId id){
        userRepository.deleteById(id);
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }



}
