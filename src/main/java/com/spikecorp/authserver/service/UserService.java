package com.spikecorp.authserver.service;

import com.spikecorp.authserver.entity.User;
import com.spikecorp.authserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository repository;

    public User createUser(@RequestBody User user) {
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Duplicate username or email");
        }
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = repository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            return repository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}
