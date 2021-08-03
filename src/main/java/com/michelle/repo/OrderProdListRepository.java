package com.michelle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.michelle.model.OrderProdList;

public interface OrderProdListRepository extends JpaRepository<OrderProdList, Long> {

	OrderProdList findById(long id);
	
	@Query("SELECT o FROM OrderProdList o WHERE o.productId = :id")
	OrderProdList findByProdId(Long id);
}
