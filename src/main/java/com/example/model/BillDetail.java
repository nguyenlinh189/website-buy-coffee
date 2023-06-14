package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "bill_id", referencedColumnName = "id")
	private Bill bill;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
	
	private int quantity;
	public BillDetail(Bill bill, Product product, int quantity) {
		super();
		this.bill = bill;
		this.product = product;
		this.quantity = quantity;
	}
}
