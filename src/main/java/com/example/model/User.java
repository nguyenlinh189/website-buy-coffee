package com.example.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String password;
	private String name;
	private String email, role;
	private int isadmin=0;
	private boolean enable;
	
	@Enumerated(EnumType.STRING)
	private Provider provider;
	public User(String password, String name, String email, int isadmin) {
		super();
		this.password = password;
		this.name = name;
		this.email = email;
		this.isadmin = isadmin;
	}
	
}
