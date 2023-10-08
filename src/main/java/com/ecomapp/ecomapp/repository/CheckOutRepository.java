package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckOutRepository extends JpaRepository<CheckOut, UUID> {

      List<CheckOut> findByUserIdOrderByCreatedAtDesc(UUID userId);


        List<CheckOut> findByProductId(UUID productId);


        CheckOut findTopByUserIdOrderByCreatedAtDesc(UUID userId);


    }








