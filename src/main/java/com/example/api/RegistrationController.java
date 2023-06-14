package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path="/registration",produces = "application/json")
@CrossOrigin("*")
public class RegistrationController {
	@Autowired
	private UserService service;
	
	public RegistrationController(UserService service) {
		super();
		this.service = service;
	}
	@PostMapping(consumes = "application/json")
	private User register(@RequestBody User user) {
		return service.saveLocal(user);
	}
}
