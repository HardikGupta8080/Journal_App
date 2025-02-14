package com.example.JournalApp.controller;

import com.example.JournalApp.Service.JournalEntryService;
import com.example.JournalApp.Service.UserService;
import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntriesController {
  @Autowired
    private JournalEntryService journalEntryService;
@Autowired
  UserService userService;
  @GetMapping
  public ResponseEntity<?> getall(){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String username=authentication.getName();
    User user=userService.findByUserName(username);

    if(user!=null){
      return new ResponseEntity<>(user.getJournalEntries(),HttpStatus.OK);
    }
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }
  @GetMapping("id/{id}")
  public ResponseEntity<?> getbyid(@PathVariable ObjectId id){
    Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
    String username=authentication.getName();
    User user=userService.findByUserName(username);
    List<JournalEntry> list=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).collect(Collectors.toList());
//    Optional<JournalEntry> Entry=journalEntryService.findById(id);
    if(!list.isEmpty()){
      return new ResponseEntity<>(list.get(0), HttpStatus.OK);
    }
    else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody JournalEntry journalEntry) {
      //System.out.println("Received entry: " + journalEntry);
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      String username=authentication.getName();
      try {
        journalEntryService.saveEntry(username,journalEntry);
        return new ResponseEntity<>(journalEntry,HttpStatus.OK);

      } catch (Exception e) {
        System.out.println(e);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }
    @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable ObjectId id) {
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      String username=authentication.getName();
      User user=userService.findByUserName(username);
      List<JournalEntry> list=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();
    if(!list.isEmpty()) {
        journalEntryService.delete(id,username);
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id,@RequestBody JournalEntry journalEntry) {
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
      String username=authentication.getName();
      User user=userService.findByUserName(username);
      List<JournalEntry> list=user.getJournalEntries().stream().filter(x->x.getId().equals(id)).toList();
    if(!list.isEmpty()) {
        JournalEntry old=journalEntryService.findById(id).get();
        old.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().equals("")? journalEntry.getTitle(): old.getTitle());
        old.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().equals("")? journalEntry.getContent(): old.getContent());
        journalEntryService.SaveEntry(old);
        return new ResponseEntity<>(HttpStatus.OK);
      } else {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }
    }

}
