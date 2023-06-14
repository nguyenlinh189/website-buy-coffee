package com.example.dto;

import com.example.model.Product;
import com.example.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private int id;
	private int quantity;
	private float total;
	private User user;
	private Product product;
}
