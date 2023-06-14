package com.example.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CartDto;
import com.example.model.Cart;
import com.example.model.Product;
import com.example.model.User;
import com.example.repo.CartRepository;
import com.example.repo.ProductRepository;
import com.example.repo.UserRepository;

@RestController
public class CartController {
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("/cart/{id}")
	public CartDto getCardById(@PathVariable int id) {
		Cart cart = cartRepo.findById(id);
		return new CartDto(cart.getId(),cart.getQuantity(),cart.getTotal(),cart.getUser(),cart.getProduct());
	}
	@PostMapping("/cart/add")
	public CartDto addToCart(@RequestBody Cart cart) {
		Product p = productRepo.findById(cart.getProduct().getId());
		cart.setProduct(p);
		User u = userRepo.findById(cart.getUser().getId());
		cart.setUser(u);
		cart = cartRepo.save(cart);
		return new CartDto(cart.getId(),cart.getQuantity(),cart.getTotal(),cart.getUser(),cart.getProduct());
	}
	@DeleteMapping("/cart/delete")
	public void deleteCart(@RequestParam int id) {
		cartRepo.deleteById(id);
	}
	@GetMapping("/cart/user")
	public List<CartDto> getCart(int userId){
		List<Cart> carts = cartRepo.findByUserId(userId);
		List<CartDto> cartList = new ArrayList<>();
		for(Cart i : carts) {
			Product p = productRepo.findById(cartRepo.getProductIdByCartId(i.getId()));
			i.setProduct(p);
			User u = userRepo.findById(userId);
			i.setUser(u);
			CartDto c = new CartDto(i.getId(), i.getQuantity(),i.getTotal(),u,p);
			cartList.add(c);
		}
		return cartList;
	}
	@PostMapping("/cart/update")
	public CartDto updateCart(@RequestBody Cart cart) {
		Product p = productRepo.findById(cart.getProduct().getId());
		cart.setProduct(p);
		User u = userRepo.findById(cart.getUser().getId());
		cart.setUser(u);
		cart = cartRepo.save(cart);
		return new CartDto(cart.getId(),cart.getQuantity(),cart.getTotal(),cart.getUser(),cart.getProduct());
	}
	@GetMapping("/cart/check")
	public String check(@RequestParam int userId, @RequestParam int productId) {
		Cart cart = cartRepo.findByUserIdAndProductId(userId, productId);
		if(cart==null) {
			return "Not found";
		}
		else {
			return "Exist";
		}
	}
	@GetMapping("/cart/checkInventory")
	public String checkInventory(@RequestParam int productId, @RequestParam int quantity) {
		Product p = productRepo.findById(productId);
		if(p.getQuantity() >= quantity) {
			return "Stocking";
		}
		else {
			return "Out of stock";
		}
	}
}
