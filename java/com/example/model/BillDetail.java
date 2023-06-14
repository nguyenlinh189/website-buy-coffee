package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {
	private int id;
	private Bill bill;
	private Product product;
	private int quantity;
	public BillDetail(Bill bill, Product product, int quantity) {
		super();
		this.bill = bill;
		this.product = product;
		this.quantity = quantity;
	}
}
