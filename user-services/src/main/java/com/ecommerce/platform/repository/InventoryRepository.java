package com.ecommerce.platform.repository;
import com.ecommerce.platform.entity.Inventory;
import com.ecommerce.platform.entity.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    @Query("""
        SELECT i
        FROM Inventory i
        JOIN FETCH i.product
        WHERE i.id = :inventoryId
        """)
    Optional<Inventory> findByIdWithProduct(@Param("inventoryId") Long inventoryId);

    @Query("""
        SELECT i
        FROM Inventory i
        JOIN FETCH i.product
        """)
    List<Inventory> findAllWithProduct();
    
    boolean existsByProductId(Long productId);
    
    Optional<Inventory> findByProduct(Product product);
}