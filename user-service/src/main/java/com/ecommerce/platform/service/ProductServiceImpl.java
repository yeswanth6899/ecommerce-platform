package com.ecommerce.platform.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.platform.dto.ProductRequest;
import com.ecommerce.platform.dto.ProductResponse;
import com.ecommerce.platform.entity.Category;
import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.Product;
import com.ecommerce.platform.entity.ProductAvailabilityStatus;
import com.ecommerce.platform.exception.CategoryNotFoundException;
import com.ecommerce.platform.exception.InventoryNotFoundException;
import com.ecommerce.platform.exception.ProductAlreadyExistsException;
import com.ecommerce.platform.exception.ProductNotFoundException;
import com.ecommerce.platform.mapper.ProductMapper;
import com.ecommerce.platform.repository.CategoryRepository;
import com.ecommerce.platform.repository.InventoryRepository;
import com.ecommerce.platform.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private static final int LOW_STOCK_THRESHOLD = 5;

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    private final InventoryRepository inventoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ProductMapper productMapper,
                              InventoryRepository inventoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {

        Category category = fetchCategoryById(request.getCategoryId());

        validateDuplicateProduct(request.getName());

        Product product = productMapper.toEntity(request, category);

        Product savedProduct = productRepository.save(product);

        return buildProductResponse(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {

        Product product = fetchProductById(id);

        return buildProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::buildProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(Long categoryId) {

        fetchCategoryById(categoryId);

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::buildProductResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = fetchProductById(id);

        Category category = fetchCategoryById(request.getCategoryId());

        validateDuplicateProductForUpdate(id, request.getName());

        productMapper.updateEntity(product, request, category);

        Product updatedProduct = productRepository.save(product);

        return buildProductResponse(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Product product = fetchProductById(id);

        productRepository.delete(product);
    }

    // ==========================================================
    // Helper Methods
    // ==========================================================

    private Category fetchCategoryById(Long categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: " + categoryId));
    }

    private Product fetchProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with id: " + id));
    }

    private void validateDuplicateProduct(String productName) {

        if (productRepository.existsByName(productName)) {

            throw new ProductAlreadyExistsException(
                    "Product already exists with name: " + productName);
        }
    }

    private void validateDuplicateProductForUpdate(Long productId, String productName) {

        if (productRepository.existsByNameAndIdNot(productName, productId)) {

            throw new ProductAlreadyExistsException(
                    "Product already exists with name: " + productName);
        }
    }

    private ProductResponse buildProductResponse(Product product) {

        ProductResponse response = productMapper.toResponse(product);

        response.setAvailabilityStatus(getAvailabilityStatus(product));

        return response;
    }

    private ProductAvailabilityStatus getAvailabilityStatus(Product product) {

        Inventory inventory = inventoryRepository.findByProduct(product)
                .orElseThrow(() ->
                        new InventoryNotFoundException(
                                "Inventory not found for product: "
                                        + product.getName()));

        if (inventory.getAvailableStock() == 0) {
            return ProductAvailabilityStatus.OUT_OF_STOCK;
        }

        if (inventory.getAvailableStock() <= LOW_STOCK_THRESHOLD) {
            return ProductAvailabilityStatus.LOW_STOCK;
        }

        return ProductAvailabilityStatus.IN_STOCK;
    }
}