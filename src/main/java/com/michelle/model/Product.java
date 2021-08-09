package com.michelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private long productId;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "product_price")
	private float productPrice;
	@Column(name="instock_qty")
	private int instockQty;
	@Column(name="product_category")
	private String productCategory;
	

	public Product(long productId, String productName, float productPrice, int instockQty, String productCategory) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.instockQty = instockQty;
		this.productCategory = productCategory;
	}
	
}
