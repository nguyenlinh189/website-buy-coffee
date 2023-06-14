package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.model.Contact;

@Controller
@RequestMapping("/contact")
public class ContactController {
	private RestTemplate rest=new RestTemplate();
	@GetMapping
	private String get() {
		return "contact";
	}
	
	@PostMapping
	private String get(@RequestParam(name="name")String name,@RequestParam(name="email")String email,@RequestParam(name="title")String title,@RequestParam(name="content")String content,Model model) {
		Contact c= new Contact(name, email, title, content);
		rest.postForObject("http://localhost:8082/contact", c, Contact.class);
		model.addAttribute("message", "Gửi thành công");
		return "contact";
	}
}
