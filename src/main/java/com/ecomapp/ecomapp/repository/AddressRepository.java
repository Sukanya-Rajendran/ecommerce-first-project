package com.ecomapp.ecomapp.repository;


import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> findByUser(User user);

    List<Address> findByIsDeletedFalse();


// Arrays findByUserEmail(String email);
}
