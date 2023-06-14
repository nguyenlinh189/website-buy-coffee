package com.example.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String addressShip;
	private String phoneShip;
	private String nameShip;
	private String paymentType;
	private Date dateCreate;
	private int status;
	
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "user_Id", referencedColumnName = "id")
	private User user;
	
	public Bill(String addressShip, String phoneShip, String nameShip, String paymentType, User user) {
		super();
		this.addressShip = addressShip;
		this.phoneShip = phoneShip;
		this.nameShip = nameShip;
		this.paymentType = paymentType;
		this.user = user;
	}
}
