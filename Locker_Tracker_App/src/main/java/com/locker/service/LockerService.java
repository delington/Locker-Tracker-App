package com.locker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locker.repository.LockerRepository;

@Service
public class LockerService {
    
    private LockerRepository lockerRepo;

    @Autowired
    public void setLockerRepo(LockerRepository lockerRepo) {
        this.lockerRepo = lockerRepo;
    }
    
}
