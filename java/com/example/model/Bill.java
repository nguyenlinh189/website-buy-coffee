package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
	private int id;
	private String addressShip;
	private String phoneShip;
	private String nameShip;
	private String paymentType;
	private Date dateCreate;
	private User user;
	private int status;
	public Bill(String addressShip, String phoneShip, String nameShip, String paymentType, Date dateCreate, User user, int status) {
		super();
		this.addressShip = addressShip;
		this.phoneShip = phoneShip;
		this.nameShip = nameShip;
		this.paymentType = paymentType;
		this.dateCreate = dateCreate;
		this.user = user;
		this.status=status;
	}
	
	
}
