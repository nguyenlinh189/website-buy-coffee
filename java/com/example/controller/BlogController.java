package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.example.model.Blog;
import com.example.model.CategoryBlog;
import com.example.model.User;

@Controller
@RequestMapping("/blog")
public class BlogController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping
	private String get(Model model, HttpSession session, @RequestParam(name = "page") String xpage) {
		List<CategoryBlog> listCateBlog = Arrays.asList(rest.getForObject("http://localhost:8082/categoryblog/getall", CategoryBlog[].class));
		session.setAttribute("listCateBlog", listCateBlog);
		List<Blog> listBlog = Arrays.asList(rest.getForObject("http://localhost:8082/blog/getall", Blog[].class));
		int size = listBlog.size(), page = 1;
		int num = 1;
		if (size % 5 == 0)
			num = size / 5;
		else
			num = size / 5 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 5;
		end = Math.min(page * 5, size);
		model.addAttribute("listNum", listNum);
		model.addAttribute("page", page);
		session.setAttribute("listBlog", listBlog.subList(start, end));
		String url = "/blog?page=";
		model.addAttribute("url", url);

		// lay nhung bai dang gan day
		List<Blog> listBlogTop4 = listBlog.subList(0, 5);
		session.setAttribute("listBlogTop4", listBlogTop4);
		return "blog";
	}

	@GetMapping("/getbycate")
	private String getByCate(@RequestParam(name = "idCategory") int idcate, Model model, HttpSession session,
			@RequestParam(name = "page") String xpage) {
		List<Blog> listBlog = Arrays
				.asList(rest.getForObject("http://localhost:8082/blog/getbycate?idCategory=" + idcate, Blog[].class));
		int size = listBlog.size(), page = 1;
		int num = 1;
		if (size % 5 == 0)
			num = size / 5;
		else
			num = size / 5 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 5;
		end = Math.min(page * 5, size);
		listBlog = listBlog.subList(start, end);
		model.addAttribute("listNum", listNum);
		System.out.print(num + " " + size);
		model.addAttribute("page", page);
		session.setAttribute("listBlog", listBlog);
		String url = "/blog/getbycate?idCategory=" + idcate + "&page=";
		session.setAttribute("url", url);
		return "blog";
	}
	
	@GetMapping("/detail")
	private String getBlogDetail(@SessionAttribute(name = "listBlog") List<Blog> listBlog,
			@RequestParam(name = "idBlog") int id, Model model) {
		Blog blog = new Blog();
		for (Blog b : listBlog)
			if (b.getId() == id)
				blog = b;
		List<String> listContent = Arrays.asList(blog.getContent().split("xdong"));
		model.addAttribute("listContent", listContent);
		model.addAttribute("blog", blog);
		// danh sach nhung bai viet lien quan
		List<Blog> listRelative = new ArrayList<>();
		int count = 1;
		for (Blog i : listBlog)
			if (i.getId() != id && count != 4) {
				listRelative.add(i);
				count++;
			}
		model.addAttribute("listRelative", listRelative);
		return "blogDetail";
	}

	@GetMapping("/search")
	private String searchByKey(@RequestParam(name = "key") String key, Model model, HttpSession session,
			@RequestParam(name = "page") String xpage) {
		List<Blog> listBlog = Arrays
				.asList(rest.getForObject("http://localhost:8082/blog/search?key=" + key, Blog[].class));
		int size = listBlog.size(), page = 1;
		int num = 1;
		if (size % 5 == 0)
			num = size / 5;
		else
			num = size / 5 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 5;
		end = Math.min(page * 5, size);
		listBlog = listBlog.subList(start, end);
		model.addAttribute("listNum", listNum);
		System.out.print(num + " " + size);
		model.addAttribute("page", page);
		session.setAttribute("listBlog", listBlog);
		String url = "/blog/search?key=" + key + "&page=";
		session.setAttribute("url", url);
		return "blog";
	}

}
