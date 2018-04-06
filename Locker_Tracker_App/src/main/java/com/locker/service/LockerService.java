package com.locker.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locker.exception.LockerException;
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

    public void addOwner(String email, Integer lockerId) {
        User loggedInUser = userService.getLoggedInUserByName(email);
        Locker oldLocker = lockerRepo.findOneByOwner(loggedInUser);
        Locker newLocker = lockerRepo.findOneById(lockerId);

        if (oldLocker != null) {
            if (oldLocker.getId().equals(newLocker.getId())) {
                throw new IllegalArgumentException("Locker change for itself is useless!");
            }

            oldLocker.setOwner(null);
            lockerRepo.save(oldLocker);
        }

        newLocker.setOwner(loggedInUser);
        lockerRepo.save(newLocker);

        log.info("Change User's locker successfully done.");
    }

    public void removeLocker(Integer lockerId, String email) throws LockerException {
        Locker oldLocker = lockerRepo.findOneById(lockerId);

        if (oldLocker == null) {
            log.error("User tried to remove locker which is not his/her. lockerId=[{}]", lockerId);
            throw new LockerException("User has no such locker to remove!");
        }

        oldLocker.setOwner(null);
        lockerRepo.save(oldLocker);

        log.info("Remove logged in User's locker succesfully done.");
    }

}
