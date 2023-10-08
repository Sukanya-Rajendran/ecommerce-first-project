package com.ecomapp.ecomapp.controller.admincontroller.usermanagement;


import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/manage")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/listAllUsers")
    public List<User> listAllUsers(){
        return userService.listUsers();
    }
}
