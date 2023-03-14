package com.springboot.practise.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.practise.datamodel.Todo;
import com.springboot.practise.service.TodoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TodoController {
	private TodoService todoService;

	public TodoController(TodoService todoService) {
		super();
		this.todoService = todoService;
	}

	@GetMapping("/users/{username}/todos")
	public List<Todo> getUserTodos(@PathVariable String username) {
		return todoService.findByUserName(username);
	}

	@GetMapping("/users/{username}/todos/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		return todoService.findById(id);
	}

	@DeleteMapping("/users/{username}/todos/{id}")
	@ResponseBody
	public ResponseEntity deleteTodoById(@PathVariable String username, @PathVariable long id) {
		Todo todo = todoService.deleteById(id);
		if (todo != null) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	// list-todos
	@RequestMapping("/list-todos")
	public String listAllTodos(ModelMap model) {
		model.addAttribute("todos", todoService.findAll());
		return "listTodos";
	}

	@PutMapping("/users/{username}/todos/{id}")
	@ResponseBody
	public ResponseEntity updateTodo(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo) {
		Todo todoUpdated = todoService.save(todo);
		return new ResponseEntity<Todo>(todo, HttpStatus.OK);
	}

	@PostMapping("/users/{username}/todos")
	@ResponseBody
	public ResponseEntity createTodo(@PathVariable String username, @RequestBody Todo todo) {
		Todo createdTodo = todoService.save(todo);
		
		//location
		//Get current resource url
		//{id} append id to it
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		
		return  ResponseEntity.created(uri).build();
	}
	
}
