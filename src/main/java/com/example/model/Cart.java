package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int quantity;
	private float total;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_Id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_Id", referencedColumnName = "id")
	private Product product;
}
