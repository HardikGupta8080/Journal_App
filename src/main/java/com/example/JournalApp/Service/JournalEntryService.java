package com.example.JournalApp.Service;


import com.example.JournalApp.Repository.JournalEntryRepository;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {




    @Autowired
    JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;
    @Transactional
    public JournalEntry saveEntry(String username, JournalEntry journalEntry){
        User user=userService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());

        journalEntryRepository.save(journalEntry);
        JournalEntry saved=journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
        return journalEntry;
    }
    public void SaveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    @Transactional
    public void delete(ObjectId id, String username){
        User user=userService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

}
