package com.michelle.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.michelle.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{


}

//UI->controller->service->repo->database