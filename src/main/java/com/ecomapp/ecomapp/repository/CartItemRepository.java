package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    void deleteById(UUID id);


}
