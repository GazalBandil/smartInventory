package com.smartInventory.backend.controller;

import com.smartInventory.backend.dtos.LoginRequest;
import com.smartInventory.backend.dtos.UserRequest;
import com.smartInventory.backend.enums.Role;
import com.smartInventory.backend.model.User;
import com.smartInventory.backend.security.JwtUtil;
import com.smartInventory.backend.service.CustomUserDetailsService;
import com.smartInventory.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // ✅ Login Admin and Generate JWT Token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate user credentials
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername());

        // Return the token
        return ResponseEntity.ok(token);
    }



    //  Create a new User (Admin-Only)
    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserRequest user) {
        ResponseEntity<?> createdUser = userService.createUser(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getContactNo(),
                Role.USER);
        return ResponseEntity.ok(createdUser);
    }

    //  Create another Admin (Admin-Only)
    @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody User user) {
        ResponseEntity<?> createdAdmin = userService.createUser(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getPassword(),
                user.getContactNo()
                , Role.ADMIN);
        return ResponseEntity.ok(createdAdmin);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response){
        response.setHeader("Authorization", "");
        return ResponseEntity.ok("Logged out successfully");
    }


}
