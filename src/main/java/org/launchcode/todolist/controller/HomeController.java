//package org.launchcode.todolist.controller;
//
//
//import org.launchcode.todolist.models.User;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//
//@Controller
//public class HomeController {
//
//        @GetMapping("/")
//        public String displayIndexPage(Model model) {
//            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            model.addAttribute("user", user);
//            model.addAttribute("title", "landing");
//            return "index";
//        }
//    }