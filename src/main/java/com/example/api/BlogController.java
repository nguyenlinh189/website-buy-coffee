package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.BlogDto;
import com.example.service.BlogService;


@RestController
@RequestMapping(path = "/blog",produces = "application/json")
@CrossOrigin("*")
public class BlogController {
	@Autowired
	private BlogService blogservice;

	public BlogController(BlogService blogservice) {
		super();
		this.blogservice = blogservice;
	}
	
	@GetMapping("/getbycate")
	private ResponseEntity<List<BlogDto>>getBlogsByCategory(@RequestParam(name="idCategory")int idcate){
		List<BlogDto>list=blogservice.getBlogsByCategoryDecs(idcate);
		return new ResponseEntity<List<BlogDto>>(list,HttpStatus.OK);
	}
	//lay tat ca danh sach theo ngay giam dan
	@GetMapping("/getall")
	private ResponseEntity<List<BlogDto>>getAll(){
		List<BlogDto>list=blogservice.getAll();
		return new ResponseEntity<List<BlogDto>>(list,HttpStatus.OK);
	}
	// lay danh sach theo tu khoa
	@GetMapping("/search")
	private ResponseEntity<List<BlogDto>>getByKey(@RequestParam(name="key")String key){
		List<BlogDto>list=blogservice.searchBlogs(key);
		return new ResponseEntity<List<BlogDto>>(list,HttpStatus.OK);
	}
}
