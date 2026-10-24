package com.example.notepad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// Service layer: all the business logic lives here.
@Service
public class UserService {

    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Returns the user if username and password are correct, otherwise null
    public User login(String username, String password) {
        User user = repo.findByUsername(username);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            return null;
        }
        return user;
    }

    // Returns false if the username is already taken
    public boolean register(String fullName, String email, String username, String password) {
        if (repo.findByUsername(username) != null) {
            return false;
        }
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(encoder.encode(password)); // encrypt before saving
        repo.save(user);
        return true;
    }

    public User getUser(String username) {
        return repo.findByUsername(username);
    }

    public void saveNotes(String username, String notes) {
        User user = repo.findByUsername(username);
        user.setNotes(notes);
        repo.save(user);
    }
}
