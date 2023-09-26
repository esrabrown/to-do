package org.launchcode.todolist.controller;

import org.launchcode.todolist.models.TodoItem;
import org.launchcode.todolist.models.data.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

//    @GetMapping
//    public String index() {
//        return "index";
//    }

}

//    @RequestMapping("")
//    public String index(Model model){
//        model.addAttribute("todoitems", todoItemRepository.findAll());
//        return "index";
//    }

//    @GetMapping("add")
//    public String displayTodoItems(Model model){
//        model.addAttribute(new TodoItem());
//        model.addAttribute("todoitem", todoItemRepository.findAll());
//        return "index";
//    }

