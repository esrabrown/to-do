package org.launchcode.todolist.models.data;


import org.launchcode.todolist.models.TodoItem;
import org.launchcode.todolist.models.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Integer> {

}
