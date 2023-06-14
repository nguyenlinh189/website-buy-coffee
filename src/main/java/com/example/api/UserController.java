package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repo.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepo;
	@GetMapping("/all")
	public List<User> getAllUser(){
		return userRepo.findAll();
	}
	@PostMapping("/save")
	public User saveUser(@RequestBody User user) {
		User u = userRepo.findByEmail(user.getEmail());
		if(u == null)
			return userRepo.save(user);
		else return u;
	}

}
