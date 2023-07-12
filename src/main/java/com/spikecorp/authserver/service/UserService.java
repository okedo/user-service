package com.spikecorp.authserver.service;

import com.spikecorp.authserver.entity.User;
import com.spikecorp.authserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    public User createUser(@RequestBody User user) {
        return repository.save(user);
    }
}
