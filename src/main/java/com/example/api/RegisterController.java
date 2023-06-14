package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping(path = "/register",produces = "application/json")
@CrossOrigin("*")
public class RegisterController {
	@Autowired
	EntityLinks entityLinks;
	private UserService userservice;
	
	public RegisterController(UserService userservice) {
		super();
		this.userservice = userservice;
	}
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	private User add(@RequestBody User u) {
		return userservice.saveUser(u);
	}
}
