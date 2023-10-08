package com.ecomapp.ecomapp.controller.OtpController;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Otp;
import com.ecomapp.ecomapp.service.OtpService;
import com.ecomapp.ecomapp.service.OtpServiceImpl;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/otp")
public class OtpController {
    @Autowired
    OtpServiceImpl otpserviceimpl;
    @Autowired
    OtpService otpService;
    @Autowired
    UserService userService;




    @GetMapping("/otpPage")
    public String getOtpPage(@RequestParam (name="error" ,defaultValue = " ") String error ,   Model model) {
        model.addAttribute("error" ,error);
        model.addAttribute("invalid" , "invalid");
        model.addAttribute("resend" , "resend");
        model.addAttribute("success" , "success");

        return "/OtpPage";
    }

    @PostMapping("/otpSubmit")
    public String proccessOtp(HttpServletRequest request, HttpSession session) {
        String enteredOtp = request.getParameter("enteredOtp");

        String userEmail = (String) session.getAttribute("email");

        if (userEmail != null) {
            Otp otp = otpService.getStoredOtpByEmail(userEmail); // Implement this method in your otpService
            String storedOtp   = otp.getOtpname();

            if (storedOtp != null && enteredOtp != null && storedOtp.equals(enteredOtp)) {

                UserDto userDTO = (UserDto) session.getAttribute("user");

                userService.registerNewCustomer(userDTO);
                System.out.println(userDTO.toString());

                return "redirect:/";
            }
        }

        return "redirect:/OtpOne?error=invalid";
    }
//    @GetMapping("/reSendOtp")
//    public String resendOtp(HttpServletRequest request, HttpSession session) {
//        UserDto userDto = (UserDto) session.getAttribute("customerDTO");
//        if (userDto != null) {
//            otpService.sentOtp(userDto, session);
//            return "redirect:/otpPage?success=resend";
//        }
//        return "redirect:/register"; // Handle the case where customerDTO is not found
//    }

}












