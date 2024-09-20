package org.launchcode.todolist.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.launchcode.todolist.models.TodoItem;
import org.launchcode.todolist.models.TodoList;
import org.launchcode.todolist.models.data.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:3000")
//@Controller
//public class TodoItemController {
//
//    @Autowired
//    private TodoItemRepository todoItemRepository;
//
////    @GetMapping
////    public List<TodoItem> findAll(){
////        return todoItemRepository.findAll();
////    }
//
////    @GetMapping("/")
////    public String displayTodoItems(Model model){
////        List<TodoItem> todoList = todoItemRepository.findAll();
////        model.addAttribute("items", new TodoItem(todoList));
//////        model.addAttribute(new TodoList());
////        model.addAttribute("newItem", new TodoList());
////
////        return "index";
////    }
//
//    // Endpoint to retrieve all TodoItems
//    @GetMapping("todos")
//    public List<TodoItem> getAllTodoItems() {
//        return this.todoItemRepository.findAll();
//    }
//
////    @GetMapping("todos")
////    public List<TodoItemDto> getAllTodoItems() {
////        List<TodoItem> todoItems = this.todoItemRepository.findAll();
////        return todoItems.stream()
////                .map(TodoItemDto::fromEntity)
////                .collect(Collectors.toList());
////    }
//
//
//    // Endpoint to create a new TodoItem
//    @PostMapping("create")
//    public ResponseEntity<?> createTodoItem(@RequestBody @Valid TodoItem todoItem) {
//        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
//        return ResponseEntity.ok(savedTodoItem);
//    }
//
//    // Endpoint to update the completion status of a TodoItem
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateTodoItemCompletion(@PathVariable int id, @RequestParam boolean completed) {
//        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);
//        if (optionalTodoItem.isPresent()) {
//            TodoItem todoItem = optionalTodoItem.get();
//            todoItem.setCompleted(completed);
//            todoItemRepository.save(todoItem);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // Endpoint to delete a TodoItem by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteTodoItem(@PathVariable int id) {
//        todoItemRepository.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//
//
//
//
////
////
////    @GetMapping("/")
////    public String displayTodoItems(Model model){
////      model.addAttribute("title", "Todo List");
////      model.addAttribute("items",  todoItemRepository.findAll());
////      return "index";
////   }
////
////    @GetMapping("/create")
////    public String displayCreateItems(Model model){
////      model.addAttribute("title", "Create Item");
////      model.addAttribute("item", new TodoItem());
////      return "create";
////    }
////
////    @PostMapping("/create")
////    public String createTodoItem(@ModelAttribute @Valid TodoItem todoItem, Errors errors,
////                                 HttpServletRequest request, Model model){
////        model.addAttribute("errors" , errors);
////            if(errors.hasErrors()){
////                return "/create";
////            }
////
//////        TodoItem item = new TodoItem(todoItem.getTitle(), todoItem.getDescription(),todoItem.getDueDate(), todoItem.isCompleted());
////        todoItemRepository.save(todoItem);
////            return "redirect:/";
////    }
////
////    @GetMapping("/delete")
////    public String displayDeleteItems(Model model){
////        model.addAttribute("title", "Delete Items");
////        model.addAttribute("items", todoItemRepository.findAll());
////        return "delete";
////    }
////
////    @PostMapping("/delete")
////    public String processDeleteItems(@RequestParam(required = false) int[] itemIds){
////
////      if(itemIds != null){
////          for(int id : itemIds){
////              todoItemRepository.deleteById(id);
////          }
////      }
////      return "redirect:";
////    }
//
////    @PostMapping("/update")
////    public String updateTodoItem(@ModelAttribute TodoList updateItems) {
////        for (TodoList updateItems : requestItem.getTodoList() )}
////        TodoItem item = new TodoItem()
////    }
//
//
//}

//@CrossOrigin(origins = "http://localhost:3000")
//@RestController
//@RequestMapping("api/")
//public class TodoItemController {
//
//    @Autowired
//    private TodoItemRepository todoItemRepository;
//
//    // Endpoint to retrieve all TodoItems
//    @GetMapping("todos")
//    public List<TodoItem> getAllTodoItems() {
//        return this.todoItemRepository.findAll();
//    }
//
//    // Endpoint to create a new TodoItem
//    @PostMapping("create")
//    public ResponseEntity<?> createTodoItem(@RequestBody @Valid TodoItem todoItem) {
//        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
//        return ResponseEntity.ok(savedTodoItem);
//    }
//
//    // Endpoint to update the completion status of a TodoItem
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateTodoItemCompletion(@PathVariable int id, @RequestParam boolean completed) {
//        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);
//        if (optionalTodoItem.isPresent()) {
//            TodoItem todoItem = optionalTodoItem.get();
//            todoItem.setCompleted(completed);
//            todoItemRepository.save(todoItem);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//
//        // Endpoint to delete a TodoItem by ID
//        @DeleteMapping("/{id}")
//        public ResponseEntity<?> deleteTodoItem ( @PathVariable int id){
//            todoItemRepository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        }
//    }


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/")
public class TodoItemController {

    @Autowired
    private TodoItemRepository todoItemRepository;

    // Retrieve all TodoItems (could be secured for authenticated users)
    @GetMapping("todos")
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> todoItems = todoItemRepository.findAll();
        return ResponseEntity.ok(todoItems);
    }

    // Create a new TodoItem
    @PostMapping("create")
    public ResponseEntity<?> createTodoItem(@RequestBody @Valid TodoItem todoItem, Authentication authentication) {
        // Ensure the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not authenticated");
        }

        // Save the todo item
        TodoItem savedTodoItem = todoItemRepository.save(todoItem);
        return ResponseEntity.ok(savedTodoItem);
    }

    // Update the completion status of a TodoItem
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTodoItemCompletion(@PathVariable int id, @RequestParam boolean completed) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            todoItem.setCompleted(completed);
            todoItemRepository.save(todoItem);
            return ResponseEntity.ok(todoItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo item not found");
        }
    }

    // Delete a TodoItem by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoItem(@PathVariable int id) {
        if (todoItemRepository.existsById(id)) {
            todoItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo item not found");
        }
    }
}
