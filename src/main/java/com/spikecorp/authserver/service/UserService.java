package com.spikecorp.authserver.service;

import com.spikecorp.authserver.entity.User;
import com.spikecorp.authserver.exception.DuplicateDataException;
import com.spikecorp.authserver.exception.UserNotFoundException;
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
        Boolean noUserName = user.getUsername() == null || user.getUsername().trim().isEmpty();
        Boolean noPassword = user.getPassword() == null || user.getPassword().trim().isEmpty();
        Boolean noEmail = user.getEmail() == null || user.getEmail().trim().isEmpty();
        if (noUserName || noPassword || noEmail) {
            throw new IllegalArgumentException("incorrect data");
        }
        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateDataException("Duplicate username or email");
        }
    }

    public User getUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("No user with specified ID"));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user with the specified ID"));

        if (updatedUser.getUsername() != null && !updatedUser.getUsername().isEmpty()
                && !existingUser.getUsername().equals(updatedUser.getUsername())) {
            validateUniqueUsername(updatedUser.getUsername(), id);
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()
                && !existingUser.getEmail().equals(updatedUser.getEmail())) {
            validateUniqueEmail(updatedUser.getEmail(), id);
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()
                && !existingUser.getPassword().equals(updatedUser.getPassword())) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return repository.save(existingUser);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private void validateUniqueUsername(String username, Long id) {
        User usernameUser = repository.findByUsername(username);
        if (usernameUser != null && !usernameUser.getId().equals(id)) {
            throw new DuplicateDataException("Username is already in use");
        }
    }

    private void validateUniqueEmail(String email, Long id) {
        User emailUser = repository.findByEmail(email);
        if (emailUser != null && !emailUser.getId().equals(id)) {
            throw new DuplicateDataException("Email is already in use");
        }
    }
}
