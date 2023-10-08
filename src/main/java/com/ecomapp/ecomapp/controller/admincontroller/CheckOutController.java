package com.ecomapp.ecomapp.controller.admincontroller;





import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.Cart;
import com.ecomapp.ecomapp.model.CheckOut;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.CartRepository;
import com.ecomapp.ecomapp.service.CartService.CartService;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
public class CheckOutController {
    @Autowired
    private UserService userService;
    @Autowired
    AddressService addressService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;



    @GetMapping("/all")
    public String allCheckouts(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());

//        System.out.println(prinicipal.getName());
        List<Address> addresses = addressService.getNonDeleteAddressByCustomer(user);
        for (Address userad : addresses) {
            System.out.println(userad);
        }
        List<Cart>avaliablecartitem=cartRepository.findAll();
        System.out.println(avaliablecartitem.get(0).getProduct().getImages().get(0)+"---------------");
        model.addAttribute("avaliablecartitem", avaliablecartitem);
        model.addAttribute("total",cartService.getTotalPrice(principal.getName()));


        model.addAttribute("address", addresses);
        model.addAttribute("user", user);
        return "/user/CheckOut";
    }
}


