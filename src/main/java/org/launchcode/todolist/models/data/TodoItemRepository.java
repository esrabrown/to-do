package org.launchcode.todolist.models.data;


import org.launchcode.todolist.models.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {

}
