package com.ecomapp.ecomapp.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class Otp {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)

    private int id;
    private String otpname;

    private LocalDateTime createdtime;
    private LocalDateTime  ExpiryTime;
    private String  email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtpname() {
        return otpname;
    }

    public void setOtpname(String otpname) {
        this.otpname = otpname;
    }

    public LocalDateTime getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(LocalDateTime createdtime) {
        this.createdtime = createdtime;
    }

    public LocalDateTime getExpiryTime() {
        return ExpiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        ExpiryTime = expiryTime;
    }
}