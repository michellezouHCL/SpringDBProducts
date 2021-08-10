package com.michelle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.michelle.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

	@Query(value = "select * from Product p where CONCAT(p.product_name, p.product_id, p.product_price) LIKE %:keyword%", nativeQuery=true)
	List<Product> findByKeyword(@Param("keyword")String keyword);
	
}

//UI->controller->service->repo->database