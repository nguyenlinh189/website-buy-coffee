package com.example.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Product{
	private int id;
	private String name, linkImg;
	private float price, discount;
	private int quantity, amountsold, status;
	private float priceDiscount;
	private Category category;
	public Product(int id, String name, String linkImg, float price, float discount, int quantity, int amountsold,
			int status) {
		super();
		this.id = id;
		this.name = name;
		this.linkImg = linkImg;
		this.price = price;
		this.discount = discount;
		this.quantity = quantity;
		this.amountsold = amountsold;
		this.status = status;
	}
}
