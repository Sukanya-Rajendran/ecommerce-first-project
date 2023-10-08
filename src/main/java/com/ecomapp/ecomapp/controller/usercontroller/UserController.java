package com.ecomapp.ecomapp.controller.usercontroller;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
 @Autowired
 private AddressService addressService;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping
    public String showAdminPage(){

        return "user/main-page";
    }

    @GetMapping("/address/add")
    public String addAddress(Model model){
        return "user/address-form";
    }

    @PostMapping("address/save")
    public String saveAddress(Model model, RedirectAttributes redirectAttributes,
                              @ModelAttribute Address address){
        Optional<User> user = Optional.ofNullable(userService.findByUsername(getCurrentUsername ()));
        if(user.isPresent()){
            User existingUser=user.get();
            address.setUser(existingUser);

            addressService.save(address);
            model.addAttribute("user",user);
            redirectAttributes.addFlashAttribute("success","Succesfuuly added");
            return "redirect:/profile/details";
        }else{
            redirectAttributes.addFlashAttribute("error","User not found");
            return "redirect:/address/add";
        }




    }
    @PostMapping("/address/update")

    public String updateAddress(@ModelAttribute Address address,
                                Model model){


        return "redirect:/user/address";
    }


    @GetMapping("/address/update/{id}")

    public String updateAddress(@PathVariable UUID id,
                                Model model){

        Address address = addressService.findById(id).orElse(null);

        model.addAttribute("address",address);

        return "user/update-address";

    }

}
