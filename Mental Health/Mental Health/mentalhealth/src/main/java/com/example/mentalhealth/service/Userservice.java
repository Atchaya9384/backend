package com.example.mentalhealth.service;

import com.example.mentalhealth.model.User;
import com.example.mentalhealth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Userservice {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        validatePasswordMatch(user.getPassword(), user.getConfirmPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, User userDetails) {
        validatePasswordMatch(userDetails.getPassword(), userDetails.getConfirmPassword());
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
        user.setConfirmPassword(userDetails.getConfirmPassword());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void validatePasswordMatch(String password, String confirmPassword) {
        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }
    }
}
