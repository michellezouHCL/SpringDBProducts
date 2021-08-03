package com.michelle.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.michelle.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{
	@Query("SELECT (p.productQty * p.productPrice) FROM Product p where p.productId = :productId")
	public float getProductTotalById(@Param("productId") Long productId);
}

//UI->controller->service->repo->database