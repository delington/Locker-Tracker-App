package com.locker.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.locker.model.Locker;
import com.locker.model.User;

@Repository
public interface LockerRepository extends MongoRepository<Locker, String> {

    @Override
    public List<Locker> findAll();

    public Locker findOneByOwnerId(User user);

    public Locker findByIdAndOwnerId(String id, User user);

    public Locker findOneById(Integer lockerId);

    public List<Locker> findByOwnerId(String id);

}
