package com.ecommerce.platform.dto;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class ProductRequest {
	
	@NotBlank(message = "Product name is required")
	private String name;
	@NotNull(message = "Price is required")
	@Positive(message = "Price must be greater than zero")
	private BigDecimal price;
	@NotBlank(message = "Product description is required")
	private String description;
	@NotNull(message = "Stock Quantity is required")
	@PositiveOrZero(message = "Stock Quantity cannot be negative")
	private Integer stockQuantity;
	@NotBlank(message = "Image url is required")
	@URL(message = "Url should be valid")
	@Size(max = 1000, message = "Image URL must not exceed 1000 characters")
	private String imageUrl;
	@NotNull(message = "Category Id is required")
	@Positive(message = "Category ID must be greater than zero")
	private Long categoryId;
	
	
	public ProductRequest() {
		
	}


	public ProductRequest(String name, BigDecimal price, String description, Integer stockQuantity, String imageUrl,Long categoryId) {
		
		this.name = name;
		this.price = price;
		this.description = description;
		this.stockQuantity = stockQuantity;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getStockQuantity() {
		return stockQuantity;
	}


	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


	public Long getCategoryId() {
		return categoryId;
	}


	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
}
