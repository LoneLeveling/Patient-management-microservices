package com.pm.authservice.service;

import com.pm.authservice.model.User;
import com.pm.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    //Injecting User repo. into our UserService using Dependency Injection.
    public UserService(UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }
public Optional<User> findByEmail(String email)
{
    return userRepository.findByEmail(email);

}

}
