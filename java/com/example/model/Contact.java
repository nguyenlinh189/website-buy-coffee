package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
	private int id;
	private String name;
	private String email;
	private String title;
	private String content;
	public Contact(String name, String email, String title, String content) {
		this.name = name;
		this.email = email;
		this.title = title;
		this.content = content;
	}
}
