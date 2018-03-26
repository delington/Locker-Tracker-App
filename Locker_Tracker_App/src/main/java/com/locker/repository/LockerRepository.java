package com.locker.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.locker.model.Locker;
import com.locker.model.User;

@Repository
public interface LockerRepository extends CrudRepository<Locker, Integer> {
    public List<Locker> findAll();
    
    public Locker findOneByOwner(User user);

    public Locker findOneById(Integer lockerId);
}
