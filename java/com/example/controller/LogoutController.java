package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/logout")
@SessionAttributes("name")
public class LogoutController {
	@GetMapping
	private String get(Model model,HttpSession session)
	{
		model.addAttribute("name",null);
		session.setAttribute("name", null);
		session.setAttribute("user", null);
		session.setAttribute("cart", null);
		session.setAttribute("cartsize", 0);
		session.setAttribute("subtotal", 0);
		session.setAttribute("total", 0);
		return "index";
	}
}
