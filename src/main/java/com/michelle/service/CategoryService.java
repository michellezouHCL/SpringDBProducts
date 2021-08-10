package com.michelle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.michelle.model.Category;
import com.michelle.model.Product;
import com.michelle.repo.CategoryRepository;


@Service
public class CategoryService {

	@Autowired
	private CategoryRepository cr;

	
	
	public List<Category> listAll() {
		return cr.findAll();
	}
	
	public void save(Category category) {
		cr.save(category);
	}
	
	public Category getCategoryById(long id) {
		return cr.findById(id).get();
	}

	public void deleteCategory(long id) {
		cr.deleteById(id);
	}
	
}
