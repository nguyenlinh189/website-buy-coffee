package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryBlogDto;
import com.example.service.CategoryBlogService;

@RestController
@RequestMapping(path = "categoryblog",produces = "application/json")
@CrossOrigin("*")
public class CategoryBlogController {
	@Autowired
	private CategoryBlogService service;

	public CategoryBlogController(CategoryBlogService service) {
		super();
		this.service = service;
	}
	
	@GetMapping("/getall")
	private ResponseEntity<List<CategoryBlogDto>> getCategoriesBlog(){
		List<CategoryBlogDto>list=service.getAll();
		return ResponseEntity.ok(list);
	}
}
