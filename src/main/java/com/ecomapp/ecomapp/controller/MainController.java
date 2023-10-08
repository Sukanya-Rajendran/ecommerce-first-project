package com.ecomapp.ecomapp.controller;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.OtpService;
import com.ecomapp.ecomapp.service.OtpServiceImpl;
import com.ecomapp.ecomapp.service.ProductService.ProductService;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    private OtpService otpService;
    private UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    ProductService productService;

    @Autowired
    OtpServiceImpl otpServiceimpl;
    @Autowired
    private UserRepository userRepo;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public MainController(UserService userService) {
        super();
        this.userService = userService;
    }


    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("login")
    public String login() {
        System.out.println("reached login");
        return "login";
    }


    @PostMapping("signup")
    public String registerUserAccount(@ModelAttribute("user") UserDto userDto, HttpSession session) throws MessagingException, UnsupportedEncodingException {
        int otp = otpService.generateRandomNumber();
        System.out.println(otp);
        System.out.println(userDto.getEmail());
        session.setAttribute("email", userDto.getEmail());
        session.setAttribute("user", userDto);
        otpServiceimpl.saveOtp(userDto, otp);
        otpService.sentOtpToEmail(userDto.getEmail(), otp);

        return "redirect:/getOtpPage";
    }


     @GetMapping("getOtpPage")
      public String getOtpPage() {
        return "/Otp/OtpNew";
      }


    @GetMapping
    public String redirectToLandingPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        boolean isUser = authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

        if (isAdmin) return "redirect:/adminMainPage";

        return "index";


    }

    @GetMapping("/shopView")
    public String shopview(Model model) {
        List<Product> avaliableproducts = productService.findAll();
        model.addAttribute("avaliableproduct", avaliableproducts);
        System.out.println("is it  right ");
        return "user/shop";
    }
}
//
//@GetMapping("/cart")
//    public String cartView(){
//        return "user/cart";
//}
//
//
//}
