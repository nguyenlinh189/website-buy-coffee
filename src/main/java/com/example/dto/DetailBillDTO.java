package com.example.dto;

import com.example.model.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DetailBillDTO {
	private int id;
	private int quantity;
	private Product product;
}
