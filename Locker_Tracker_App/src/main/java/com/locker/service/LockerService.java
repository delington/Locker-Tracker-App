package com.locker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locker.model.Locker;
import com.locker.model.User;
import com.locker.repository.LockerRepository;

@Service
public class LockerService {
    
    private static final Logger log = LoggerFactory.getLogger(LockerService.class);
    
    private LockerRepository lockerRepo;
    
    private UserService userService;

    @Autowired
    public void setLockerRepo(LockerRepository lockerRepo) {
        this.lockerRepo = lockerRepo;
    }
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public List<Locker> getLockers() {
        return lockerRepo.findAll();
    }

    public void editOwner(String email, Integer lockerId) {
        User loggedInUser = userService.getLoggedInUserByName(email);
        Locker oldLocker = lockerRepo.findOneByOwner(loggedInUser);
        Locker newLocker = lockerRepo.findOneById(lockerId);
        
        oldLocker.setOwner(null);
        newLocker.setOwner(loggedInUser);
        lockerRepo.save(oldLocker);
        lockerRepo.save(newLocker);
        
        log.info("Change User's locker successfully done.");
    }

    public void removeLocker(String email) {
        User loggedInUser = userService.getLoggedInUserByName(email);
        Locker oldLocker = lockerRepo.findOneByOwner(loggedInUser);
        
        oldLocker.setOwner(null);
        lockerRepo.save(oldLocker);
        
        log.info("Remove logged in User's locker succesfully done.");
    }
    
}
