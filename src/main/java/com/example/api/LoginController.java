package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

@RestController
@RequestMapping(path = "login", produces = "application/json")
@CrossOrigin("*")
public class LoginController {
	@Autowired
	private UserService userService;
	EntityLinks entityLinks;
	public LoginController(UserService userService) {
		super();
		this.userService = userService;
	}
	@GetMapping
	private User checkUser(@RequestParam(name="email")String email,@RequestParam(name="password")String password) {
		User u=userService.checkUser(email, password);
		return u;
	}
	@GetMapping("/checkAdmin")
	private User checkAdmin(@RequestParam(name="email")String email,@RequestParam(name="password")String password)
	{
		User u=userService.checkUser(email, password);
		if(u!=null&&u.getIsadmin()==1)
			return u;
		return null;
	}
}
