package com.ecommerce.platform.dto;

import java.math.BigDecimal;

import com.ecommerce.platform.entity.ProductAvailabilityStatus;

public class ProductResponse {
	
	private Long id;
	private String name;
	private BigDecimal price;
	private String description;
	private String imageUrl;
	private Long categoryId;
	private String categoryName;
	private ProductAvailabilityStatus availabilityStatus;
	
	
	public ProductResponse() {
		
	}


	


	public ProductResponse(Long id, String name, BigDecimal price, String description, String imageUrl, Long categoryId,
			String categoryName, ProductAvailabilityStatus availabilityStatus) {
		
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.availabilityStatus = availabilityStatus;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getCategoryName() {
		return categoryName;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public ProductAvailabilityStatus getAvailabilityStatus() {
		return availabilityStatus;
	}

	public void setAvailabilityStatus(ProductAvailabilityStatus availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}	

}
