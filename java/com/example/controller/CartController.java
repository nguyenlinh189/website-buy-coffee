package com.example.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.model.CartItem;
import com.example.model.Product;
import com.example.model.User;

@Controller
@RequestMapping("/")
public class CartController {
	private RestTemplate rest = new RestTemplate();
	private List<CartItem> cart=new ArrayList<>();
	
	@GetMapping("/cart")
	public String showCart(Model model, HttpSession session,Principal principal) {
		User user = (User) session.getAttribute("user");
		if(user==null && principal==null) {
			return "redirect:/login";
		}
		cart = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
		if(cart == null) cart = new ArrayList<>();
		float subtotal = 0;
		for(CartItem item:cart) {
			item.getProduct().setPriceDiscount(item.getProduct().getPrice()-item.getProduct().getPrice()*item.getProduct().getDiscount()/100);
			item.setTotal(item.getProduct().getPriceDiscount()*item.getQuantity());
			subtotal += item.getProduct().getPriceDiscount();
			
		}
		model.addAttribute("cartitem", new CartItem());
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("total", subtotal);
		session.setAttribute("cartsize", cart.size());
		model.addAttribute("cart", cart);
		session.setAttribute("cart", cart);
		return "cart";
	}
	@GetMapping("/addToCart")
	public String addToCart(@RequestParam int productId,Model model, HttpSession session, Principal principal) {
		User user = (User) session.getAttribute("user");
		if(user==null && principal==null) {
			return "redirect:/login";
		}
		
		// kiem tra xem gio hang da co chua
		cart = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
		if(cart == null) cart = new ArrayList<>();
		Product product = rest.getForObject("http://localhost:8082/category-parent/category/product/"+productId, Product.class);
		String exist = rest.getForObject("http://localhost:8082/cart/check?userId="+user.getId()+"&productId="+productId, String.class);
		if(exist.equals("Exist") ){
			model.addAttribute("mess", "Sản phẩm đã có trong giỏ hàng");
			return "redirect:/cart";
		}
		
		// them san pham vao gio hang
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setQuantity(1);
		item.setUser(user);
		item.setTotal(product.getPriceDiscount());
		item=rest.postForObject("http://localhost:8082/cart/add", item, CartItem.class);
		ArrayList<CartItem> list = new ArrayList<>();
		list.addAll(cart);
		list.add(item);
		float subtotal = 0;
		for(CartItem i:list) {
			i.getProduct().setPriceDiscount(i.getProduct().getPrice()-i.getProduct().getPrice()*i.getProduct().getDiscount()/100);
			i.setTotal(i.getProduct().getPriceDiscount()*i.getQuantity());
			subtotal += i.getProduct().getPriceDiscount()*i.getQuantity();
		}
		
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("cartsize", list.size());
		session.setAttribute("total", subtotal+30000);
		model.addAttribute("cart", list);
		session.setAttribute("cart", list);
		return "cart";
	}
	@GetMapping(value = "/addCart")
	public String addCart(@RequestParam int productId, @RequestParam int quantity, Model model, HttpSession session,
			Principal principal) {
		User user = (User) session.getAttribute("user");
		if (user == null && principal == null) {
			return "redirect:/login";
		}
		// kiem tra xem gio hang da co chua
		cart = Arrays
				.asList(rest.getForObject("http://localhost:8082/cart/user?userId=" + user.getId(), CartItem[].class));
		if (cart == null)
			cart = new ArrayList<>();
		Product product = rest.getForObject("http://localhost:8082/category-parent/category/product/" + productId,
				Product.class);
		String exist = rest.getForObject(
				"http://localhost:8082/cart/check?userId=" + user.getId() + "&productId=" + productId, String.class);
		if (exist.equals("Exist")) {
			model.addAttribute("mess", "Sản phẩm đã có trong giỏ hàng");
			return "redirect:/cart";
		}
		// kiểm tra xem còn đủ số lượng không
		String checkInventory = rest.getForObject(
				"http://localhost:8082/cart/checkInventory?productId=" + productId + "&quantity=" + quantity,
				String.class);
		if (checkInventory.equals("Out of stock")) {
			model.addAttribute("mess", "Sản phẩm không còn đủ số lượng");
			return "redirect:/cart";
		}
		cart = Arrays
				.asList(rest.getForObject("http://localhost:8082/cart/user?userId=" + user.getId(), CartItem[].class));
		// them san pham vao gio hang
		CartItem item = new CartItem();
		item.setProduct(product);
		item.setQuantity(quantity);
		item.setUser(user);
		item.setTotal(product.getPriceDiscount());
		item = rest.postForObject("http://localhost:8082/cart/add", item, CartItem.class);
		ArrayList<CartItem> list = new ArrayList<>();
		list.addAll(cart);
		list.add(item);
		float subtotal = 0;
		for (CartItem i : list) {
			i.getProduct().setPriceDiscount(
					i.getProduct().getPrice() - i.getProduct().getPrice() * i.getProduct().getDiscount() / 100);
			i.setTotal(i.getProduct().getPriceDiscount() * i.getQuantity());
			subtotal += i.getProduct().getPriceDiscount() * i.getQuantity();
		}

		session.setAttribute("subtotal", subtotal);
		session.setAttribute("cartsize", list.size());
		session.setAttribute("total", subtotal + 30000);
		model.addAttribute("cart", list);
		session.setAttribute("cart", list);
		return "cart";
	}
	@GetMapping("/deleteFromCart")
	public String deleteFromCart(@RequestParam int productId,Model model, HttpSession session, Principal principal) {
		User user = (User) session.getAttribute("user");
		if(user==null && principal==null) {
			return "redirect:/login";
		}
		cart = (List<CartItem>) session.getAttribute("cart");
		List<CartItem> list=new ArrayList<>();
		list.addAll(cart);
		for(CartItem cartitem:list) {
			if(cartitem.getProduct().getId()==productId) {
				list.remove(cartitem);
				rest.delete("http://localhost:8082/cart/delete?id="+cartitem.getId());
				break;
			}
		}
		float subtotal = 0;
		for(CartItem i:list) {
			subtotal += i.getProduct().getPriceDiscount()*i.getQuantity();
		}
		session.setAttribute("cartsize", list.size());
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("total", subtotal+30000);
		session.setAttribute("cart", list);
		model.addAttribute("cart", list);
		return "cart";
	}
	@GetMapping(value="/updateCart")
	public String updateCart(@RequestParam int id, @RequestParam int quantity,Model model, HttpSession session,Principal principal) {
		User user = (User) session.getAttribute("user");
		if(user==null && principal==null) {
			return "redirect:/login";
		}
		cart = Arrays.asList(rest.getForObject("http://localhost:8082/cart/user?userId="+user.getId(), CartItem[].class));
		ArrayList<CartItem> list = new ArrayList<>();
		list.addAll(cart);
		CartItem c=new CartItem();
		float subtotal = 0;
		for(CartItem item:list)
		{
			if(item.getId()==id)
			{
				item.setQuantity(quantity);
				c=item;
				String checkInventory = rest.getForObject("http://localhost:8082/cart/checkInventory?productId="+item.getProduct().getId()+"&quantity="+quantity, String.class);
				if(checkInventory.equals("Out of stock")) {
					model.addAttribute("mess", "Sản phẩm không còn đủ số lượng đặt");
					return "redirect:/cart";
				}
			}
			item.getProduct().setPriceDiscount(item.getProduct().getPrice()-item.getProduct().getPrice()*item.getProduct().getDiscount()/100);
			item.setTotal(item.getQuantity()*item.getProduct().getPriceDiscount());
			subtotal += item.getTotal();
			
		}
		rest.postForObject("http://localhost:8082/cart/update", c, CartItem.class);
		session.setAttribute("subtotal", subtotal);
		session.setAttribute("cartsize", cart.size());
		session.setAttribute("total", subtotal+30000);
		model.addAttribute("cart", list);
		session.setAttribute("cart", list);
		return "cart";
	}
	
}
