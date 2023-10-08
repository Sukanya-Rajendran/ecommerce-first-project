package com.ecomapp.ecomapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order")
    public String orderpage(){
        return "/user/OrderSuccessPage";
    }
}
