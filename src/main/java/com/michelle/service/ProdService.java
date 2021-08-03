package com.michelle.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michelle.exception.ProdNotFoundException;
import com.michelle.model.Product;
import com.michelle.repo.ProductRepository;



@Service
public class ProdService {

	@Autowired
	private ProductRepository pr;

	
	public List<Product> listAll() {
		return pr.findAll();
	}
	
	public void save(Product prod) {
		pr.save(prod);
	}
	
	public Product getProdById(long id) {
		return pr.findById(id).get();
	}
	/*
	public Product addOne(long id) throws ProdNotFoundException {
		Product p1=pr.findById(id).orElseThrow(()->new ProdNotFoundException("product not found"));
		if(p1.getInstockQty()> p1.getProductQty() && p1.getProductQty()>-1)
			p1.setProductQty(p1.getProductQty()+1);
		p1.setProductTotal(p1.getProductPrice()*p1.getProductQty());
		
		return p1;
	}
	
	public Product minusOne(long id) throws ProdNotFoundException {
		Product p1=pr.findById(id).orElseThrow(()->new ProdNotFoundException("product not found"));
		if(p1.getInstockQty()>= p1.getProductQty() && p1.getProductQty()>0)
			p1.setProductQty(p1.getProductQty()-1);
		p1.setProductTotal(p1.getProductPrice()*p1.getProductQty());
		return p1;
	}
	*/
	public void deleteProd(long id) {
		pr.deleteById(id);
	}

	/*
	 * public Product clearCart(Long id) throws ProdNotFoundException { Product
	 * p1=pr.findById(id).orElseThrow(()->new
	 * ProdNotFoundException("product not found")); p1.setProductQty(0);
	 * p1.setProductTotal(0); return p1; }
	 */
	
	
	
}
