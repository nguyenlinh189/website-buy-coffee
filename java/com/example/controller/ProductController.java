package com.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.example.model.Category;
import com.example.model.Product;

@Controller
@RequestMapping("/")
public class ProductController {
	private RestTemplate rest = new RestTemplate();

	@GetMapping("/shop")
	public String showPageProducts(@RequestParam(name="page")String xpage, Model model, HttpSession session) {
		List<Product> products = new ArrayList<>();
		products = Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/products/all", Product[].class));
		products=getListProducts(products);
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		model.addAttribute("size", products.size());
		session.setAttribute("listproduct", products);
		return "shop";
	}
	@GetMapping("/product-details")
	public String showProductDetail(Model model, @RequestParam int id, HttpSession session) {
		Product product = rest.getForObject("http://localhost:8082/category-parent/category/product/"+id, Product.class);
		product.setPriceDiscount(product.getPrice()-product.getPrice()*product.getDiscount()/100);
		Category category = rest.getForObject("http://localhost:8082/category-parent/category?productId="+product.getId(), Category.class);
		List<Product> relatedProducts = new ArrayList<>();
		relatedProducts = Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/category/products?categoryId="+category.getId(), Product[].class));
		List<Product> upsellProducts = new ArrayList<>();
		upsellProducts = Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/category/products/search?name=Bánh", Product[].class));
		relatedProducts=getListProducts(relatedProducts);
		upsellProducts=getListProducts(upsellProducts);
		model.addAttribute("relatedProducts", relatedProducts);
		model.addAttribute("upsellProducts", upsellProducts);
		model.addAttribute("product", product);
		model.addAttribute("category", category);
		return "product-details";
	}
	// lay danh sach theo categoryp
	@GetMapping("/categoryp")
	private String getProductByCategoryP(@RequestParam(name="nameCateP")String nameCateP,@RequestParam(name="page")String xpage, Model model,HttpSession session) {
		List<Product>products=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/categoryp?nameCateP="+nameCateP, Product[].class));
		products=getListProducts(products);
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		model.addAttribute("size", products.size());
		session.setAttribute("listproduct", products);
		return "shop";
	}
	//lay danh sach san pham theo categoriid
	@GetMapping("/shop/category")
	private String getProductByCategoryID(@RequestParam(name="nameCate")String nameCate,Model model,@RequestParam(name="page")String xpage,HttpSession session) {
		List<Product>products=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/category/search-products?categoryName="+nameCate,Product[].class));
		products=getListProducts(products);
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		session.setAttribute("listproduct", products);
		return "shop";
	}
	@GetMapping("/search")
	//lay danh sach theo o search
	private String searchByName(@RequestParam(name="nameP")String nameP,Model model,@RequestParam(name="page")String xpage, HttpSession session) {
		List<Product>products=Arrays.asList(rest.getForObject("http://localhost:8082/category-parent/category/products/search?name="+nameP,Product[].class));
		products=getListProducts(products);
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		model.addAttribute("size", products.size());
		session.setAttribute("listproduct", products);
		return "shop";
	}
	@GetMapping("/shop/page")
	private String getByPage(@RequestParam(name="page")String xpage, HttpSession session,Model model){
		List<Product>products=(List<Product>)session.getAttribute("listproduct");
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		page = Integer.parseInt(xpage);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		products=getListProducts(products);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		model.addAttribute("size", products.size());
		session.setAttribute("listproduct", products);
		return "shop";
	}
	// sap xep san pham
	@GetMapping("/shop/sort")
	private String sort(@RequestParam(name="orderby")String orderby, HttpSession session,Model model){
		List<Product>products=(List<Product>)session.getAttribute("listproduct");
		if(orderby.equalsIgnoreCase("1"))
            getListByKhuyenMai(products);
        else if(orderby.equalsIgnoreCase("2"))
            getListByNameA_Z(products);
        else if(orderby.equalsIgnoreCase("3"))
            getListByNameZ_A(products);
        else if(orderby.equalsIgnoreCase("4"))
            getListByPriceHightLow(products);
        else if(orderby.equalsIgnoreCase("5"))
            getListByPriceLowHight(products);
        else if(orderby.equalsIgnoreCase("6"))
            getListByBanChay(products);
		products=getListProducts(products);
		int size = products.size(), page = 1;
		int num = 1;
		if (size % 9 == 0)
			num = size / 9;
		else
			num = size / 9 + 1;
		List<Integer> listNum = new ArrayList<>();
		for (int i = 1; i <= num; i++)
			listNum.add(i);
		int start, end;
		start = (page - 1) * 9;
		end = Math.min(page * 9, size);
		model.addAttribute("products", products.subList(start, end));
		model.addAttribute("pages",listNum);
		model.addAttribute("page",page);
		model.addAttribute("size", products.size());
		session.setAttribute("listproduct", products);
		session.setAttribute("orderby", orderby);
		return "shop";
	}
	//set pricediscount
	private List<Product> getListProducts(List<Product>list){
		for(Product p:list) {
			p.setPriceDiscount(p.getPrice()-p.getPrice()*p.getDiscount()/100);
		}
		return list;
	}
	private void getListByNameA_Z(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    private void getListByNameZ_A(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return o2.getName().compareToIgnoreCase(o1.getName());
            }
        });
    }

    private void getListByPriceLowHight(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return Double.compare(o1.getPriceDiscount(), o2.getPriceDiscount());
            }
        });
    }

    private void getListByPriceHightLow(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return Double.compare(o2.getPriceDiscount(), o1.getPriceDiscount());
            }
        });
    }
    private void getListByKhuyenMai(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return Double.compare(o2.getDiscount(),o1.getDiscount());
            }
        });
    }
    private void getListByBanChay(List<Product> list) {
        Collections.sort(list, new Comparator<Product>() {
            public int compare(Product o1, Product o2) {
                return Integer.compare(o2.getAmountsold(),o1.getAmountsold());
            }
        });
    }
}
