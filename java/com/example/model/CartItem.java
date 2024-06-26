package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem{
	private int id;
	private int quantity;
	private float total;
	private User user;
	private Product product;
}
