package com.alamara.journalApp.service;

import com.alamara.journalApp.Entity.JournalEntry;
import com.alamara.journalApp.Entity.User;
import com.alamara.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName)
    {
        try{
            User dbUser = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            dbUser.getJournalEntries().add(saved);
            userService.saveUser(dbUser);
        } catch (Exception e) {
            log.error("error ",e);
            throw new RuntimeException("An error Occurred while sending the request");
        }
    }

    public List<JournalEntry> getAllByUser()
    {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id)
    {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName)
    {
        boolean removed=false;
        try {
            User dbUser = userService.findByUserName(userName);
             removed = dbUser.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(dbUser);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error occur while deleting the entry",e);
        }
    return removed;
    }

    public void saveEntry(JournalEntry journalEntry)
    {
       journalEntryRepository.save(journalEntry);
    }
}
