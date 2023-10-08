package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {

    List<Cart> findByUser_Email(String email);

    Optional<Cart> findByUserAndProduct(User user, Product product);



}


