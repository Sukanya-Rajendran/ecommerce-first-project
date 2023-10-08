package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp,Integer>
{
    public Otp findByEmail(String email);
}

