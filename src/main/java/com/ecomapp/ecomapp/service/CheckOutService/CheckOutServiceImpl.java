package com.ecomapp.ecomapp.service.CheckOutService;

import com.ecomapp.ecomapp.model.CheckOut;
import com.ecomapp.ecomapp.repository.CheckOutRepository;
import com.ecomapp.ecomapp.service.CheckOutService.CheckOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CheckOutServiceImpl implements CheckOutService {


    @Autowired
    private CheckOutRepository checkoutRepository;


    @Override
    public CheckOut saveCheckOut(CheckOut checkOut) {
        return null;
    }
    @Override
    public List<CheckOut> getAllCheckouts() {
        return checkoutRepository.findAll();
    }


    @Override
    public CheckOut getCheckOutById(UUID id) {
        return null;
    }
}