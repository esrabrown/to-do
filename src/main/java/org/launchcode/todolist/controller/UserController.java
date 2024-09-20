package org.launchcode.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.launchcode.todolist.models.dto.RegisterFormDTO;
import org.launchcode.todolist.models.User;
import org.launchcode.todolist.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;


//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("api/users")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping("/authentication/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute(new RegisterFormDTO());
//        model.addAttribute("title", "Register");
//        return "/authentication/register";
//    }
//
//    @PostMapping("/authentication/register")
//    public String registerUser(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors,
//                               HttpServletRequest request, Model model) {
//        model.addAttribute("errors", errors);
//        if (errors.hasErrors()) {
//            return "/authentication/register";
//        }
//
//        User existingUser = userRepository.findByEmail(registerFormDTO.getEmail());
//
//        if (existingUser != null) {
//            errors.rejectValue("email", "email.alreadyExists", "A user with that email already exists");
//            return "/authentication/register";
//        }
//
//        String password = registerFormDTO.getPassword();
//        String verifyPassword = registerFormDTO.getVerifyPassword();
//        if (!password.equals(verifyPassword)) {
//            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
//            return "/authentication/register";
//        }
//
//        User newUser = new User(registerFormDTO.getName(), registerFormDTO.getLastName(), registerFormDTO.getEmail(), registerFormDTO.getPassword());
//        userRepository.save(newUser);
//        return "redirect:/";
//
//    }
//
//    @GetMapping("/authentication/login")
//    public String showLoginForm() {
//        return "/authentication/login";
//    }
//}

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/authentication/register")
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

        // Save new user
        User newUser = new User(registerFormDTO.getName(), registerFormDTO.getLastName(), registerFormDTO.getEmail(),
                passwordEncoder.encode(registerFormDTO.getPassword()));
             //   registerFormDTO.getPassword());
        userRepository.save(newUser);

        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

//    @PostMapping("/authentication/login")
//    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
//        String email = credentials.get("email");
//        String password = credentials.get("password");
//        Map<String, String> response = new HashMap<>();
//
//        User user = userRepository.findByEmail(email);
//
//        if (user == null) {
//            // User not found
//            response.put("email", "No user found with this email");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        // Verify the password
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            // Password does not match
//            response.put("password", "Invalid password");
//            return ResponseEntity.badRequest().body(response);
//        }
//
//        // Authentication successful
//
//
//
//        response.put("message", "Login successful");
//        return ResponseEntity.ok(response);
//
//    }
//@PostMapping("/authentication/login")
//public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
//    String email = credentials.get("email");
//    String password = credentials.get("password");
//
//    // Fetch user from the database
//    User user = userRepository.findByEmail(email);
//    if (user == null) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found"));
//    }
//
//    // Compare provided password with stored hashed password
//    if (passwordEncoder.matches(password, user.getPassword())) {
//        return ResponseEntity.ok(Map.of("message", "Login successful"));
//    }else{
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid password"));
//    }

    @PostMapping("/authentication/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not found"));
        }

        String storedPasswordHash = user.getPassword();

        // Log the raw password and the stored password hash for debugging
        System.out.println("Raw password: " + password);
        System.out.println("Stored password hash: " + storedPasswordHash);

        if (passwordEncoder.matches(password, storedPasswordHash)) {
            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            // Log an explicit failure message
            System.out.println("Password does not match");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Invalid password"));
        }
    }


}


