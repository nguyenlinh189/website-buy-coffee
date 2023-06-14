package com.example.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Contact;
import com.example.repo.ContactRepository;

@RestController
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired
	private ContactRepository repo;
	
	@PostMapping
	private Contact addContact(@RequestBody Contact contact) {
		return repo.save(contact);
	}
}
