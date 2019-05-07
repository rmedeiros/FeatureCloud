package com.onekin.tagcloud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "products")
@Entity
public class Product {

	@Id
	@Column(name = "product_id")
	private String productId;
	@Column(name = "product_name")
	private String productName;

	public Product(String productId, String productName) {
		super();
		this.productId = productId;
		this.productName = productName;
	}

	public Product() {

	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + "]";
	}

}
