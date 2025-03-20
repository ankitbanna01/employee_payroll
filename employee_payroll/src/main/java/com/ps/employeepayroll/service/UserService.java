package com.ps.employeepayroll.service;

import com.ps.employeepayroll.model.User;
import com.ps.employeepayroll.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(User user) {
        logger.info("Registering user: " + user.getEmail());

        // Check if the email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.warn("User with email {} already exists!", user.getEmail());
            throw new RuntimeException("Email already in use!");
        }

        // Ensure role is set
        if (user.getRole() == null) {
            user.setRole(User.Role.USER); // Default to USER role
        }

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user to database
        User savedUser = userRepository.save(user);
        logger.info("User registered successfully with ID: " + savedUser.getId());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User authenticateUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // ✅ Compare the entered password with the encrypted password
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return user; // ✅ Password is correct, return user
            }
        }

        throw new RuntimeException("Invalid email or password");
    }

    public User findOrCreateGoogleUser(String email, String name) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        // ✅ Create new user if not found
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setRole(User.Role.USER); // Default to USER role for Google login

        return userRepository.save(newUser);
    }
}
