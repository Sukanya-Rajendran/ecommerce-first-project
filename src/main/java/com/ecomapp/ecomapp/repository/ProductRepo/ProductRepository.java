package com.ecomapp.ecomapp.repository.ProductRepo;

import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByProductName(String name);


    boolean existsByProductName(String productName);


    Page<Product> findByProductName(Pageable pageable, String keyword);

    Product findByProductNameLike(String s);

    Page<Product> findByProductNameLike(String keyword, Pageable pageable);



    @Query("SELECT u FROM Product u WHERE u.productName LIKE CONCAT(:product_name, '%')")
    List<Product> findByProductNameContaining(@Param("product_name") String productName);

    List<Product> findCategoryByIsAvailableTrue();

    Optional<Product> findById(UUID id);
}
