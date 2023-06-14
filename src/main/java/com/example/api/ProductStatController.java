package com.example.api;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.ProductStat;
import com.example.model.Product;
import com.example.repo.ProductRepository;


@RestController
@CrossOrigin("*")
public class ProductStatController {
	private RestTemplate rest = new RestTemplate();
	@Autowired
	private ProductRepository prepo;
	
	@GetMapping("/productstat")
	public List<com.example.dto.ProductStat> getProductStat() {
		List<Product> products = (List<Product>)prepo.findAll();
		List<ProductStat> stat = new ArrayList<>();
		Collections.sort(products,new Comparator<Product>() {
			@Override
			public int compare(Product x, Product y) {
				return y.getAmountsold() - x.getAmountsold();
			}
		});
		
		for (int i = 0; i <= 9; i++) {
			stat.add(new ProductStat(i,products.get(i).getId(),products.get(i).getName(),products.get(i).getPrice(),products.get(i).getAmountsold()));
		}
		
		
		return stat;
	}
	
	
}
