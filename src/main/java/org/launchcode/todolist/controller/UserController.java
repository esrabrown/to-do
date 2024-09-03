package org.launchcode.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.qleanquotes.models.dto.RegisterFormDTO;
import org.launchcode.todolist.models.User;
import org.launchcode.todolist.models.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/authentication/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute(new RegisterFormDTO());
        model.addAttribute("title", "Register");
        return "/authentication/register";
    }

    @PostMapping("/authentication/register")
    public String registerUser(@ModelAttribute @Valid RegisterFormDTO registerFormDTO, Errors errors,
                               HttpServletRequest request, Model model) {
        model.addAttribute("errors", errors);
        if (errors.hasErrors()) {
            return "/authentication/register";
        }

        User existingUser = userRepository.findByEmail(registerFormDTO.getEmail());

        if (existingUser != null) {
            errors.rejectValue("email", "email.alreadyExists", "A user with that email already exists");
            return "/authentication/register";
        }

        String password = registerFormDTO.getPassword();
        String verifyPassword = registerFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "/authentication/register";
        }

        User newUser = new User(registerFormDTO.getName(), registerFormDTO.getLastName(), registerFormDTO.getEmail(), registerFormDTO.getPassword());
        userRepository.save(newUser);
        return "redirect:/";

        // Encode password before saving
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        userRepository.save(user);
//
//        return "redirect:/login";
//    }
    }

    @GetMapping("/authentication/login")
    public String showLoginForm() {
        return "/authentication/login";
    }
}
