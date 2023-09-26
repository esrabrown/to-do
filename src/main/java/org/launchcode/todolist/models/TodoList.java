package org.launchcode.todolist.models;

import jakarta.persistence.Entity;
import jakarta.validation.Valid;

import java.util.ArrayList;



public class TodoList {

    @Valid
    private ArrayList<TodoItem> todoList = new ArrayList<TodoItem>();

    public TodoList(){
    }

    public TodoList(Iterable<TodoItem> items) {
        items.forEach(todoList:: add);
    }

    public TodoList(ArrayList<TodoItem> todoList){
        this.todoList = todoList;
    }

    public ArrayList<TodoItem> getTodoList(){
        return todoList;
    }

    public void setTodoList(ArrayList<TodoItem> todoList){
        this.todoList = todoList;
    }
}
