package com.springboot.practise.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.practise.datamodel.Todo;

@Service
public class TodoService {
	private static List<Todo> todos=new ArrayList<Todo>();
	static {
		todos.add(new Todo(1, "ram", "Learn AWS", LocalDate.now(), false));
		todos.add(new Todo(2, "in29min", "Learn Devops", LocalDate.now().plusYears(1), false));
		todos.add(new Todo(3, "in30min", "Learn Full Stack", LocalDate.now().plusYears(2), false));
	}
	private int idCounter;
	
	public List<Todo> findAll(){
		return todos;
	}
	
	public List<Todo> findByUserName(String username){
		List <Todo> userTodo = new LinkedList<Todo>();
		for(Todo t:todos) {
			if(t.getUsername().equalsIgnoreCase(username)) {
				userTodo.add(t);
			}
		}
		return userTodo;
		
	}
	public Todo deleteById(long id) {
		Todo todo = findById(id);
		todos.remove(todo);
		return todo;
	}

	public Todo findById(long id) {
		for(Todo todo:todos) {
			if(todo.getId()==id) {
				return todo;
			}
		}
		return null;
	}
	public Todo save(Todo todo) {
		if(todo.getId()==-1) {
			todo.setId(++idCounter);
			todos.add(todo);
		}else {
			deleteById(todo.getId());
			todos.add(todo);
		}
		return todo;
	}
}
