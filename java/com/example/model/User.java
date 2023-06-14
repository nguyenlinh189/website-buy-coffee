package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User {
	private int id;
	private String role;
	private String password;
	private String name;
	private String email;
	private int isadmin=0;
	private Provider provider;
}
