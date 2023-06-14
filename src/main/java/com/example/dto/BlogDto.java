package com.example.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@Setter
@Getter
public class BlogDto {
	private int id;
	private String title;
	private String content;
	private String image1;
	private String image2;
	private Date adddate;
	private String precontent;
	private CategoryBlogDto category;
}
