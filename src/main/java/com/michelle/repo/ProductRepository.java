package com.michelle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.michelle.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long>{

}

//UI->controller->service->repo->database