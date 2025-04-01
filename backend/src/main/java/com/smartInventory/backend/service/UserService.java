package com.smartInventory.backend.service;

import com.smartInventory.backend.enums.Role;
import com.smartInventory.backend.model.User;
import com.smartInventory.backend.repository.UserRepository;
import com.smartInventory.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    public UserService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    //  Create User (ONLY Admin Can Call This)
    public ResponseEntity<?> createUser(String email, String firstName, String lastName, String username, String password, String contactNo, Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }
        User user = new User();
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // Hash password
        user.setContactNo(contactNo);
        user.setRole(role); // Assign role
        String token = jwtUtil.generateToken(user.getUsername() , user.getRole());

         userRepository.save(user);
         return new ResponseEntity<>(token , HttpStatus.CREATED);


    }


    //  Find User By Email (For Login)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}

