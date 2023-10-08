package com.ecomapp.ecomapp.service;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Otp;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

public interface OtpService{

    public int generateRandomNumber();
    public void     sentOtpToEmail(String customerEmail, int otp) throws MessagingException, UnsupportedEncodingException;

    public Otp getStoredOtpByEmail(String userEmail);

    public void sentOtp(@Valid UserDto userDto, HttpSession session);


}
