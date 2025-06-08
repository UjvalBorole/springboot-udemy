package com.ecommerce.product.Repositories;


import com.ecommerce.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p FROM product p WHERE p.active = true AND p.stockQuantity > 0 AND  LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    // You can define custom query methods if needed, for example:
    // List<Product> findByCategory(String category);
    // List<Product> findByActiveTrue();
}
