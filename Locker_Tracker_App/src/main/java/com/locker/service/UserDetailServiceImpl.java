package com.locker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.locker.model.MyUserPrincipal;
import com.locker.model.User;
import com.locker.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository userRepo;

    @Autowired
    public void setUserRepo(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findOneByEmail(email.toLowerCase());

        if (user == null) {
            throw new UsernameNotFoundException("User not found by its email in the database!");
        }

        return new MyUserPrincipal(user);
    }

}
