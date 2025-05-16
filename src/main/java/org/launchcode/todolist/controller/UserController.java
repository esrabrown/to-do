package org.launchcode.todolist.controller;

import jakarta.validation.Valid;
import org.launchcode.todolist.models.dto.RegisterFormDTO;
import org.launchcode.todolist.models.User;
import org.launchcode.todolist.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/users/authentication")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody @Valid RegisterFormDTO registerFormDTO) {
        Map<String, String> response = new HashMap<>();

        // Validate passwords
        if (!registerFormDTO.getPassword().equals(registerFormDTO.getVerifyPassword())) {
            response.put("password", "Passwords do not match");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if the user already exists
        User existingUser = userRepository.findByEmail(registerFormDTO.getEmail());
        if (existingUser != null) {
            response.put("email", "A user with that email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        System.out.println("Registering user with raw password: " + registerFormDTO.getPassword());

        String hashedPassword = passwordEncoder.encode(registerFormDTO.getPassword());

        System.out.println("Password being saved (hashed): " + hashedPassword);

        User newUser = new User(
                registerFormDTO.getName(),
                registerFormDTO.getLastName(),
                registerFormDTO.getEmail(),
                hashedPassword
        );

        userRepository.save(newUser);

        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }


@PostMapping("/login")
public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
    String email = credentials.get("email");
    String password = credentials.get("password").trim();

    // Fetch user from the database
    User user = userRepository.findByEmail(email);
    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found"));
    }

    System.out.println("Raw password: " + password);
    System.out.println("Stored hash: " + user.getPassword());
    System.out.println("Login attempt with email: " + email);
    System.out.println("User from DB: " + user); // or log user.getEmail()



    // Compare provided password with stored hashed password
    if (passwordEncoder.matches(password, user.getPassword())) {
        return ResponseEntity.ok(Map.of("message", "Login successful"));
    } else {
        System.out.println("Password mismatch");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid password"));
    }
}


}


