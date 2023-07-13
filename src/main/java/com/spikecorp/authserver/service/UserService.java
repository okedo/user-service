package com.spikecorp.authserver.service;

import com.spikecorp.authserver.entity.User;
import com.spikecorp.authserver.exception.DuplicateDataException;
import com.spikecorp.authserver.exception.UserNotFoundException;
import com.spikecorp.authserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;

    public User createUser(@RequestBody User user) {
        validateUserData(user);

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

        updateUsername(existingUser, updatedUser);
        updateEmail(existingUser, updatedUser);
        updatePassword(existingUser, updatedUser);

        return repository.save(existingUser);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    private void validateUserData(User user) {
        if (ObjectUtils.isEmpty(user.getUsername()) || ObjectUtils.isEmpty(user.getPassword())
                || ObjectUtils.isEmpty(user.getEmail())) {
            throw new IllegalArgumentException("Incorrect data");
        }
    }

    private void updateUsername(User existingUser, User updatedUser) {
        String updatedUsername = updatedUser.getUsername();
        if (!ObjectUtils.isEmpty(updatedUsername) && !existingUser.getUsername().equals(updatedUsername)) {
            validateUniqueUsername(updatedUsername, existingUser.getId());
            existingUser.setUsername(updatedUsername);
        }
    }

    private void updateEmail(User existingUser, User updatedUser) {
        String updatedEmail = updatedUser.getEmail();
        if (!ObjectUtils.isEmpty(updatedEmail) && !existingUser.getEmail().equals(updatedEmail)) {
            validateUniqueEmail(updatedEmail, existingUser.getId());
            existingUser.setEmail(updatedEmail);
        }
    }

    private void updatePassword(User existingUser, User updatedUser) {
        String updatedPassword = updatedUser.getPassword();
        if (!ObjectUtils.isEmpty(updatedPassword) && !existingUser.getPassword().equals(updatedPassword)) {
            existingUser.setPassword(updatedPassword);
        }
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