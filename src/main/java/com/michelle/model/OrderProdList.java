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
	

	public OrderProdList(long orderId, long productId, String productName, float productPrice, int instockQty, int productQty) {
		this.orderId=orderId;
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.instockQty = instockQty;
		this.productQty = productQty;
		this.productTotal = productQty*productPrice;
	}
}
