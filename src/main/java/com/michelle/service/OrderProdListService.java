package com.michelle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michelle.exception.ProdNotFoundException;
import com.michelle.model.OrderProdList;
import com.michelle.model.Product;
import com.michelle.repo.OrderProdListRepository;
import com.michelle.repo.ProductRepository;

@Service
public class OrderProdListService {
	@Autowired
	OrderProdListRepository or;

	public void save(OrderProdList orderProdList) {
		or.save(orderProdList);
	}

	public void addToCart(Product p) {
		OrderProdList o = or.findByProdId(p.getProductId());
		if (o == null) {
			o = new OrderProdList();
			o.setProductId(p.getProductId());
			o.setProductName(p.getProductName());
			o.setInstockQty(p.getInstockQty());
			o.setProductPrice(p.getProductPrice());
			o.setProductQty(1);
			o.setProductTotal(p.getProductPrice());
			or.save(o);
		} else {
			if (o.getInstockQty() > o.getProductQty() && o.getProductQty() > -1)
				o.setProductQty(o.getProductQty() + 1);
			o.setProductTotal(o.getProductPrice() * o.getProductQty());
			or.save(o);
		}

	}

	public OrderProdList addOne(Long id) throws ProdNotFoundException {
		OrderProdList p1 = or.findByProdId(id);
		if (p1.getInstockQty() > p1.getProductQty() && p1.getProductQty() > -1)
			p1.setProductQty(p1.getProductQty() + 1);
		p1.setProductTotal(p1.getProductPrice() * p1.getProductQty());

		return p1;
	}

	public OrderProdList minusOne(Long id) throws ProdNotFoundException {
		OrderProdList p1 = or.findByProdId(id);
		if (p1.getInstockQty() >= p1.getProductQty() && p1.getProductQty() > 0)
			p1.setProductQty(p1.getProductQty() - 1);
		p1.setProductTotal(p1.getProductPrice() * p1.getProductQty());
		return p1;
	}

	public OrderProdList clearCart(long id) {
		OrderProdList p1 = or.findByProdId(id);
		p1.setProductQty(0);
		p1.setProductTotal(0);
		return p1;
	}

	public List<OrderProdList> getAll() {
		return or.findAll();
	}

	public void deleteOrder(long id) {
		or.deleteById(id);
	}
	
	public void deleteOPLByProdId(long id) {
		or.deleteByProdId(id);
	}

	public OrderProdList findByProdId(long id) {
		return or.findByProdId(id);
	}

	public OrderProdList findById(long id) {
		return or.findById(id);
	}

	public void delete(OrderProdList orderProdLists) {
		or.delete(orderProdLists);
	}
}
