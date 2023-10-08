package com.ecomapp.ecomapp.service;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Otp;
import com.ecomapp.ecomapp.repository.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;


@Service
public class OtpServiceImpl implements  OtpService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OtpRepo otpRepo;


    @Override

    public int generateRandomNumber() {
        Random random = new Random();
        int sixDigitNumber = random.nextInt(900000) + 100000;
        System.out.println(sixDigitNumber);
        return sixDigitNumber;
    }


    public void sentOtpToEmail(String customerEmail, int otp) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("sukanyarajendran3@gmail.com", "Watches");

        helper.setTo(customerEmail);
        helper.setSubject("Hey welcome to 'WATCHES' stay connected");
        String content = "<html><body style='font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #007bff;'>Hey there!</h2>"
                + "<p>We're excited to have you join 'WATCHES'. To verify your email address, please use the following One-Time Password (OTP):</p>"
                + "<p style='font-size: 24px; color: #007bff;'>" + otp + "</p>"
                + "<p>If you didn't request this OTP or have any questions, feel free to contact our support team.</p>"
                + "<p>Happy pedaling!</p>"
                + "<p style='color: #888;'>Note: This OTP is valid for a single use and will expire shortly.</p>"
                + "</body></html>";
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public Otp getStoredOtpByEmail(String userEmail) {
       return otpRepo.findByEmail(userEmail);
    }

    @Override
    public void sentOtp(UserDto userDto, HttpSession session) {

    }

//    @Override
//    public Integer   getStoredOtpByEmail(String email) {
//        Otp otp = otpRepo.findByEmail(email);
//        return (otp != null) ? otp.getOtpname(): null;
//
//    }

//    @Override
//    public void sentOtp(@ModelAttribute UserDto userDto , HttpSession session) {
//        int otp = (userDto);
//        System.out.println(otp);
//        sentOtpToEmail(userDto.getEmail(), otp);
//
//        session.setAttribute("userEmail", userDto.getEmail());
//        System.out.println(userDto.getEmail());
//
//    }


    public void saveOtp(UserDto userDto, int otp){
        otpRepo.save(buildOtp(userDto,otp));
    }

    private Otp buildOtp(UserDto userDto, int otp) {
        Otp otpone = new Otp();
        otpone.setOtpname(String.valueOf(otp));
        otpone.setEmail(userDto.getEmail());
        otpone.setCreatedtime(LocalDateTime.now());

        return otpone;
    }

}

