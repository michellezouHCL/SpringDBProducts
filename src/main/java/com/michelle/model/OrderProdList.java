package com.michelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orderListofProd")
public class OrderProdList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="order_id")
	private long orderId;
	@Column(name = "product_id")
	private long productId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_price")
	private float productPrice;
	@Column(name="instock_qty")
	private int instockQty;
	@Column(name = "product_qty")
	private int productQty;
	@Column(name = "total")
	private float productTotal;
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Lob
    private byte[] photos;

	public OrderProdList(long orderId, long productId, String productName, float productPrice, int instockQty, int productQty, Category category) {
		this.orderId=orderId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.instockQty = instockQty;
		this.productQty = productQty;
		this.productTotal = productQty*productPrice;
		this.category=category;
	}
	
	public void setCategoryById(Long id) {
		this.category.setCategoryId(id);
	}
	public void setCategoryName(String name) {
		this.category.setCategoryName(name);
	}
	
	
}
