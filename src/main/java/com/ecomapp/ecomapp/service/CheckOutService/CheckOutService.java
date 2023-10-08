package com.ecomapp.ecomapp.service.CheckOutService;


import com.ecomapp.ecomapp.model.CheckOut;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface CheckOutService {

    CheckOut saveCheckOut(CheckOut checkOut);

    List<CheckOut> getAllCheckouts();

    CheckOut getCheckOutById(UUID id);


}