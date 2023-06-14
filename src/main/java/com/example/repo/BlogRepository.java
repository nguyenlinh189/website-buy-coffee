package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.model.Blog;
import com.example.model.CategoryBlog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
	// lay blog theo danh muc sap xep theo ngay giam dan
	List<Blog>findByCategoryOrderByAdddateDesc(CategoryBlog category);
	// lay danh sach theo key
	List<Blog>findByTitleContainingOrderByAdddateDesc(String key);
}
