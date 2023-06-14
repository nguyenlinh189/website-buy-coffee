package com.example.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name, linkImg;
	private float price, discount;
	private int quantity, amountsold, status;
	
	@ManyToOne(targetEntity = com.example.model.Category.class)
	@JoinColumn(name="category_Id", referencedColumnName = "id")
	@JsonIgnore
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
