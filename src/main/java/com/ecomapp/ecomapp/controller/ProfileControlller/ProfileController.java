package com.ecomapp.ecomapp.controller.ProfileControlller;

import com.ecomapp.ecomapp.model.Address;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.UserService;
import com.ecomapp.ecomapp.service.address.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/details")
    public String UserProfile(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        List<Address> userAddress = addressService.getNonDeleteAddressByCustomer(user);
        System.out.println(user);
        for (Address Customer : userAddress) {
            System.out.println(Customer);
        }
        model.addAttribute("address", userAddress);
        model.addAttribute("user", user);
        return "/user/NewProfile";
    }

    @GetMapping
    public String showprofilepage() {
        return "/user/ProfileOne";
    }


    @GetMapping("/deleteAddress/{addressId}")
    public String deleteaddress(@PathVariable("addressId") String addressId) {
        addressService.disableAddress(UUID.fromString(addressId));
        return "redirect:/profile/details";
    }
    @GetMapping("/edit/{id}")
    public String editAddress(@PathVariable UUID id, Model model) {
        Address address = addressService.getAddressById(id);
        System.out.println(id+"=================");
        model.addAttribute("address", address);
        return "/user/edit-address";
    }

}