package org.launchcode.todolist.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.todolist.models.TodoItem;
import org.launchcode.todolist.models.TodoList;
import org.launchcode.todolist.models.data.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class TodoItemController {

    @Autowired
    private TodoItemRepository todoItemRepository;

//    @GetMapping
//    public List<TodoItem> findAll(){
//        return todoItemRepository.findAll();
//    }

//    @GetMapping("/")
//    public String displayTodoItems(Model model){
//        List<TodoItem> todoList = todoItemRepository.findAll();
//        model.addAttribute("items", new TodoItem(todoList));
////        model.addAttribute(new TodoList());
//        model.addAttribute("newItem", new TodoList());
//
//        return "index";
//    }
    @GetMapping("/")
    public String displayTodoItems(Model model){
      model.addAttribute("title", "Todo List");
      model.addAttribute("items",  todoItemRepository.findAll());
      return "index";
   }

    @GetMapping("/create")
    public String displayCreateItems(Model model){
      model.addAttribute("title", "Create Item");
      model.addAttribute("item", new TodoItem());
      return "create";
    }

    @PostMapping("/create")
    public String createTodoItem(@ModelAttribute @Valid TodoItem todoItem, Errors errors,
                                 HttpServletRequest request, Model model){
        model.addAttribute("errors" , errors);
            if(errors.hasErrors()){
                return "/create";
            }

//        TodoItem item = new TodoItem(todoItem.getTitle(), todoItem.getDescription(),todoItem.getDueDate(), todoItem.isCompleted());
        todoItemRepository.save(todoItem);
            return "redirect:/";
    }

    @GetMapping("/delete")
    public String displayDeleteItems(Model model){
        model.addAttribute("title", "Delete Items");
        model.addAttribute("items", todoItemRepository.findAll());
        return "delete";
    }

    @PostMapping("/delete")
    public String processDeleteItems(@RequestParam(required = false) int[] itemIds){

      if(itemIds != null){
          for(int id : itemIds){
              todoItemRepository.deleteById(id);
          }
      }
      return "redirect:";
    }

//    @PostMapping("/update")
//    public String updateTodoItem(@ModelAttribute TodoList updateItems) {
//        for (TodoList updateItems : requestItem.getTodoList() )}
//        TodoItem item = new TodoItem()
//    }


}
