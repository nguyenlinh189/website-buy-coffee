package com.example.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.example.model.Blog;
import com.example.model.Category;
import com.example.model.Product;
import com.example.model.User;

@Controller
@RequestMapping("/")
public class HomeController {
	private RestTemplate rest = new RestTemplate();
	
	@GetMapping()
	private String gethome(HttpSession session) {
		User user= (User) session.getAttribute("user");
		if(user == null) {
			session.setAttribute("name", null);
			session.setAttribute("user", null);
			session.setAttribute("cart", null);
			session.setAttribute("cartsize", 0);
			session.setAttribute("subtotal", 0);
			session.setAttribute("total", 0);
		}
		List<Category> listCoffee=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/getcatebyparent?idCateP=1", Category[].class));
		List<Category> listTea=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/getcatebyparent?idCateP=2", Category[].class));
		List<Category> ListCake=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/getcatebyparent?idCateP=3", Category[].class));
		

		List<Product> productsCoffee=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/categoryp?nameCateP=coffee", Product[].class));
		List<Product> productsTea=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/categoryp?nameCateP=tea", Product[].class));
		List<Product> productsBanh=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/categoryp?nameCateP=Bánh&Snack", Product[].class));
		
		productsCoffee=getListProducts(productsCoffee);
		productsTea=getListProducts(productsTea);
		productsBanh=getListProducts(productsBanh);
		
		List<Blog> listBlog = Arrays.asList(rest.getForObject("http://localhost:8082/blog/getall", Blog[].class));
		listBlog = listBlog.subList(0, 5);
		session.setAttribute("listBlog", listBlog);
		
		session.setAttribute("listCoffee", listCoffee);
		session.setAttribute("listTea", listTea);
		session.setAttribute("listCake", ListCake);
		session.setAttribute("productsCoffee", productsCoffee);
		session.setAttribute("productsTea", productsTea);
		session.setAttribute("productsBanh", productsBanh);
		
		
		return "index";
	}
	//set pricediscount
		private List<Product> getListProducts(List<Product> list){
			for(Product p:list) {
				p.setPriceDiscount(p.getPrice()-p.getPrice()*p.getDiscount()/100);
			}
			return list;
		}
}
