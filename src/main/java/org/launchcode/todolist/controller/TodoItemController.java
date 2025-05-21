
package org.launchcode.todolist.controller;

import jakarta.validation.Valid;
import org.launchcode.todolist.models.TodoItem;
import org.launchcode.todolist.models.data.TodoItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/todos") // better grouping
public class TodoItemController {

    private final TodoItemRepository todoItemRepository;

    public TodoItemController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        return ResponseEntity.ok(todoItemRepository.findAll());
    }

    @PostMapping("")
    public ResponseEntity<?> createTodoItem(@RequestBody @Valid TodoItem todoItem, Authentication authentication) {

        System.out.println("Due date: " + todoItem.getDueDate());

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("error", "User is not authenticated"));
        }
        todoItem.setCompleted(false);
        TodoItem saved = todoItemRepository.save(todoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateTodoItemCompletion(@PathVariable int id, @RequestParam boolean completed) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(id);
        if (optionalTodoItem.isPresent()) {
            TodoItem todoItem = optionalTodoItem.get();
            todoItem.setCompleted(completed);
            todoItemRepository.save(todoItem);
            return ResponseEntity.ok(todoItem);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Todo item not found"));
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoItem(@PathVariable int id) {
        if (todoItemRepository.existsById(id)) {
            todoItemRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Todo item not found"));
        }
    }
}
