package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.model.Cart;

public interface CartRepository extends CrudRepository<Cart, Integer>{
	List<Cart> findByUserId(int userId);
	Cart findById(int id);
	Cart findByUserIdAndProductId(int userId, int productId);
	@Query(value = "select product_id from cart c where c.id = ?1", nativeQuery = true)
	int getProductIdByCartId(int id);
	@Query(value = "select user_id from cart c where c.id = ?1", nativeQuery = true)
	int getUserIdByCartId(int id);
}
