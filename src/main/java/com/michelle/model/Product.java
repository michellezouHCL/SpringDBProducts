package com.michelle.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	

	public Product(long productId, String productName, float productPrice, int instockQty, Category category) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.instockQty = instockQty;
		this.category = category;
	}
	
}
