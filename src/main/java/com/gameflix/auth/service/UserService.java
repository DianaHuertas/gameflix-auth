package com.gameflix.auth.service;

import com.gameflix.auth.dto.AuthRequest;
import com.gameflix.auth.model.User;
import com.gameflix.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* ===== Methods your controller may already use ===== */

    public void registerUser(AuthRequest req) {
        register(req.getUsername(), req.getPassword());
    }

    public boolean loginUser(AuthRequest req) {
        return login(req.getUsername(), req.getPassword());
    }

    /* ===== Methods your TESTS are calling ===== */

    /** Creates a user; throws IllegalArgumentException if username exists. */
    public boolean register(String username, String rawPassword) {
        if (username == null || username.isBlank() || rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("Username and password are required");
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        String hash = passwordEncoder.encode(rawPassword);
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(hash);
        userRepository.save(u);
        return true;
    }

    /** Returns true if credentials are valid using BCrypt match. */
    public boolean login(String username, String rawPassword) {
        if (username == null || rawPassword == null) return false;
        Optional<User> found = userRepository.findByUsername(username);
        return found.isPresent() && passwordEncoder.matches(rawPassword, found.get().getPasswordHash());
    }
}
