package com.michelle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.michelle.model.OrderProdList;

@Transactional
public interface OrderProdListRepository extends JpaRepository<OrderProdList, Long> {

	OrderProdList findById(long id);
	
	@Query("SELECT o FROM OrderProdList o WHERE o.productId = :id")
	OrderProdList findByProdId(Long id);
	
	@Modifying
	@Query("DELETE FROM OrderProdList o WHERE o.productId = :id")
	void deleteByProdId(Long id);
}
