package com.locker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.locker.model.Locker;
import com.locker.repository.LockerRepository;

@Service
public class LockerService {
    
    private LockerRepository lockerRepo;

    @Autowired
    public void setLockerRepo(LockerRepository lockerRepo) {
        this.lockerRepo = lockerRepo;
    }
    
    public List<Locker> getLockers() {
        return lockerRepo.findAll();
    }

    //TODO finish
    public void edit(Integer lockerId) {
        
    }
    
}
