package com.example.controller;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.example.model.User;


@Controller
@RequestMapping("/registration")
@SessionAttributes("name")
public class RegistrationController {
	private RestTemplate rest=new RestTemplate();
	@GetMapping
	private String get(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}
	
	@PostMapping
	private String register(User user,Model model, HttpSession session) {
		User u=rest.postForObject("http://localhost:8082/registration", user, User.class);
		if(u==null)
		{
			model.addAttribute("error","email da ton tai");
			model.addAttribute("name",user.getName());
			return "registration";
		}else {
			session.setAttribute("name",user.getName());
			return "index";
		}
	}
}
