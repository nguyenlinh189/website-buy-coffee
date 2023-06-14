package com.example.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.example.model.Bill;
import com.example.model.BillDetail;
import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.Provider;
import com.example.model.User;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	private RestTemplate rest=new RestTemplate();
	@GetMapping
	private String showPayment(Model model, HttpSession session) {
		List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
		if(carts == null) return "redirect:/login";
		float subtotal = 0;
		for(CartItem i:carts) {
			subtotal += i.getTotal();
		}
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("total", subtotal+30000);
		model.addAttribute("cart", carts);
		float us=(subtotal+30000)/23000;
		Locale locale=new Locale("en","EN");
		String pattern="###,###.##";
		DecimalFormat decimalFormat=(DecimalFormat)NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern);;
		String usd=decimalFormat.format(us);
		model.addAttribute("usd",usd);
		return "typePayment";
	}
	@GetMapping("/paypal")
	private String Paypal(@RequestParam(name="name")String name,@RequestParam(name="shipping")String address,
			HttpSession session,Model model) {
		List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
		User user=carts.get(0).getUser();
		if(carts == null) return "redirect:/login";
		Bill bill=new Bill(address, ""
				+ "", name, "Paypal", new Date(), user,1);
		bill=rest.postForObject("http://localhost:8082/bill/savebill", bill,Bill.class);
		for(CartItem c:carts)
		{
			BillDetail db=new BillDetail(bill, c.getProduct(),c.getQuantity());
			rest.postForObject("http://localhost:8082/bill/saveBillDetail", db, BillDetail.class);
			rest.delete("http://localhost:8082/cart/delete?id="+c.getId());
		}
		carts = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
		session.setAttribute("cart", carts);
		session.setAttribute("subtotal", 0);
		session.setAttribute("total", 0);
		session.setAttribute("cartsize", 0);
		model.addAttribute("mark",1);
		return "typePayment";
	}
	@GetMapping("/cod")
	private String COD(HttpSession session,Model model) {
		List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
		if(carts == null) return "redirect:/login";
		float subtotal = 0;
		for(CartItem i:carts) {
			subtotal += i.getTotal();
		}
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("total", subtotal+30000);
		model.addAttribute("cart", carts);
		return "paymentcod";
	}
	@PostMapping("/cod")
	private String paymentCode(@RequestParam(name="name")String name,@RequestParam(name="address")String address,@RequestParam(name="phone")String phone,
							HttpSession session,Model model) {
		List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
		User user=carts.get(0).getUser();
		if(carts == null) return "redirect:/login";
		Bill bill=new Bill(address, phone, name, "Thanh toán khi nhận hàng", new Date(), user,0);
		bill=rest.postForObject("http://localhost:8082/bill/savebill", bill,Bill.class);
		for(CartItem c:carts)
		{
			BillDetail db=new BillDetail(bill, c.getProduct(),c.getQuantity());
			rest.postForObject("http://localhost:8082/bill/saveBillDetail", db, BillDetail.class);
			rest.delete("http://localhost:8082/cart/delete?id="+c.getId());
			rest.getForObject("http://localhost:8082/category-parent/product/update?id="+c.getProduct().getId()+"&amount="+c.getQuantity(), Product.class);
		}
		carts = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
		session.setAttribute("cart", carts);
		session.setAttribute("subtotal", 0);
		session.setAttribute("total", 0);
		session.setAttribute("cartsize", 0);
		model.addAttribute("mark",1);
		return "typePayment";
	}
}
