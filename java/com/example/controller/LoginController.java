package com.example.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.model.CartItem;
import com.example.model.User;

@Controller
@RequestMapping("/login")
public class LoginController {
	private RestTemplate rest=new RestTemplate();
	@GetMapping
	private String get(Model model) {
		return "login";
	}
	@GetMapping("/check")
	private String checkUser(@RequestParam(name="email")String email,@RequestParam(name="password")String password,Model model, HttpSession session) {
		User u=rest.getForObject("http://localhost:8082/login?email="+email+"&password="+password, User.class);
		if(u!=null) {
			List<CartItem> cart = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+u.getId(), CartItem[].class));
			session.setAttribute("cart", cart);
			float subtotal = 0;
			for(CartItem i:cart) {
				subtotal += i.getProduct().getPrice();
			}
			session.setAttribute("subtotal", subtotal);
			session.setAttribute("cartsize", cart.size());
			session.setAttribute("total", subtotal+30000);
			session.setAttribute("user", u);
			session.setAttribute("name", u.getName());
			return "redirect:/";
		}
		else {
			model.addAttribute("error","sai thong tin dang nhaps");
			return "login";
		}
	}
}
