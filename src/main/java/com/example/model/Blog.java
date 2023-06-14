package com.example.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="title",nullable = false)
	private String title;
	@Column(length = 100000)
	private String content;
	private String image1;
	private String image2;
	private Date adddate;
	@Column(length=10000)
	private String precontent;
	@ManyToOne
	//@JoinColumn(name="category_id")
	private CategoryBlog category;
	
}
