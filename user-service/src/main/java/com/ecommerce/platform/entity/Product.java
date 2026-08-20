package com.ecommerce.platform.entity;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "products")
public class Product extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Product name is required")
	@Column(nullable = false, unique = true)
	private String name;
	
	@NotNull(message= "Price is Required")
	@Positive(message = "Price must be greater than zero")
	@Column(nullable = false,  precision = 10, scale = 2)
	private BigDecimal price; 
	
	@NotBlank(message = "Product description is required")
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@NotBlank(message = "Image Url is Required")
	@URL(message = "Link should be in Url format" )
	@Column(nullable = false, length = 1000)
	private String imageUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public Product() {
		
	}

	public Product(String name, BigDecimal price, String description, String imageUrl, Category category) {
	
		this.name = name;
		this.price = price;
		this.description = description;
		this.imageUrl = imageUrl;
		this.category = category;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}	
}
