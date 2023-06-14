package com.example.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Category;
import com.example.model.CategoryParent;
import com.example.model.Product;
import com.example.repo.ProductRepository;
import com.example.service.ProductService;

@RestController
@RequestMapping("/category-parent")
public class ProductController {
	@Autowired
	private ProductService pService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@GetMapping()
	public List<CategoryParent> getCategoryParents() {
		return pService.getCategoryParents();
	}

	@GetMapping("/{id}")
	public List<Category> getCategoriesByCategoryParentId(@PathVariable int id) {
		return pService.getByCategoryParentId(id);
	}

	@GetMapping("/categories")
	public List<Category> getCategories() {
		return pService.getCategories();
	}

	@GetMapping("/category/{id}")
	public Category getCategories(@PathVariable int id) {
		return pService.getByCategoryId(id);
	}

	@GetMapping("/category")
	public Category getCategorieByProductId(@RequestParam int productId) {
		return pService.getByProductId(productId);
	}

	@GetMapping("/products/all")
	public List<Product> getAllProducts() {
		return pService.getAll();
	}

	@GetMapping("/category/products")
	public List<Product> getProductsByCategory(@RequestParam int categoryId) {
		return pService.getProductsByCategoryId(categoryId);
	}

	@GetMapping("/category/product/{id}")
	public Product getById(@PathVariable int id) {
		return pService.getProductById(id);
	}

	@GetMapping("/category/products/search")
	public List<Product> searchProducts(@RequestParam String name) {
		return pService.searchProduct(name);
	}

	@GetMapping("/category/search")
	public List<Product> searchProductsByCategoryId(@RequestParam int categoryId) {
		return pService.getProductsByCategoryId(categoryId);
	}

	@GetMapping("/category/search-products")
	public List<Product> searchProductsByCategoryName(@RequestParam String categoryName) {
		return pService.searchProductByCategoryName(categoryName);
	}

	// lay danh sach san pham theo category P
	@GetMapping("/categoryp")
	private List<Product> getProductByCateP(@RequestParam(name = "nameCateP") String nameCateP) {
		return pService.getProductByCateP(nameCateP);
	}

	// lay danh sach category by categoryParentId
	@GetMapping("/getcatebyparent")
	private List<Category> listCate(@RequestParam(name = "idCateP") int id) {
		return pService.getCategoryByCatePId(id);
	}
	//update amountsold
		@GetMapping("/product/update")
		private Product updateProduct(@RequestParam int id, @RequestParam int amount) {
			Product p = pService.getProductById(id);
			p.setAmountsold(p.getAmountsold()+amount);
			return productRepository.save(p);
		}
		@PostMapping("/update-product")
		private Product update(@RequestBody Product product,@RequestParam(name="cateid")int cateid) {
			productRepository.save(product);
			productRepository.updateCategoryProduct(cateid, product.getId());
			return product;
			
		}
		@PostMapping("/add-product")
		private Product add(@RequestBody Product product,@RequestParam(name="cateid")int cateid) {
			Product p=productRepository.save(product);
			productRepository.updateCategoryProduct(cateid, p.getId());
			return product;
		}
		@DeleteMapping("/delete-product")
		private void delete(@RequestParam int id) {
			productRepository.deleteById(id);
		}
}
