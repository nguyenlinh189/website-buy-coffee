package com.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Data
@AllArgsConstructor
public class Blog {
	private int id;
	private String title;
	private String content;
	private String image1;
	private String image2;
	private Date adddate;
	private CategoryBlog category;
	private String precontent;
}
