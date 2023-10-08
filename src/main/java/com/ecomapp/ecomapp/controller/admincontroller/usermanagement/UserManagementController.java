package com.ecomapp.ecomapp.controller.admincontroller.usermanagement;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Role;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.RoleRepositoy;
import com.ecomapp.ecomapp.repository.UserRepository;
import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/manage")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepositoy roleRepositoy;

    @GetMapping
    public String showHome(){
        return "admin/user/user";
    }


    @GetMapping("/listUser")
    public String listUsers(){
        return "admin/user/listUser";
    }
    @GetMapping("/manageUser")
    public String manageUsers(Authentication authentication,Model model){
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("loggedInUserId",user.getId());
        model.addAttribute("users",userService.listUsers());
        return "admin/user/manageUser";
    }
    @GetMapping("/addUser")
    public String showAddUserForm(@ModelAttribute("user") UserDto user){
        return "admin/user/addUser";
    }

    @PostMapping("/addUser")
    public String saveUser(@ModelAttribute("user") UserDto user, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/user/addUser"; // Return the form page with validation errors
        }

         userService.save(user);

        // Add a success message and redirect to a user list page or another appropriate page
        redirectAttributes.addFlashAttribute("successMessage", "User added successfully!");
        return "redirect:/admin/manage/manageUser";
    }

    @PostMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable(value = "id") long id) {
        System.out.println("in the delete method");
        userService.deleteUserById(id);
        return "redirect:/admin/manage/manageUser";
    }

    @GetMapping("/adminSettings")
    public String showAdminSettingsPage(Model model, Authentication authentication, @AuthenticationPrincipal UserDetails currentUser){
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("loggedInUserId",user.getId());
        List<User> users = userRepository.findAll();
        model.addAttribute("users",users);
        if (user.isBlocked()) {
            return "blocked";
        }
        return "admin/user/adminSettings";
    }

    @PostMapping("/toggleAdmin/{id}")
    public String toggleRole(@PathVariable("id") Long userId) {

        Optional<Role> role = roleRepositoy.findById(userId);

        if (role.isPresent()){
            Role userRole = role.get();
            if (userRole.getName().equals("ROLE_USER")) userRole.setName("ROLE_ADMIN");
            else userRole.setName("ROLE_USER");

            roleRepositoy.save(userRole);

        }
        else {
            return null;
        }
        return "redirect:/admin/manage/adminSettings";

    }

    @PostMapping("/toggleBlockUser/{id}")
    public String toggleBlockUser(@PathVariable("id") Long userId) {

        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()){
            User userStatus = user.get();
            userStatus.setBlocked(!userStatus.isBlocked());
            userRepository.save(userStatus);
            return "redirect:/admin/manage/manageUser";

        }
        else {
           return "redirect:/admin/manage/manageUser";
        }


//        return ":/admin/manage/adminSettings";

    }

    @PostMapping("/updateUser/{id}")
    public String updateUserForm(@PathVariable("id") Long userId, Model model, Authentication authentication) {

        String username = authentication.getName();
        User currentUser = userService.findByUsername(username);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID: " + userId));

        model.addAttribute("user", user);
        model.addAttribute("userId", userId); // Add the userId to be used in the form action
        if (currentUser.isBlocked()){
            return "blocked";
        }
        return "admin/user/updateUser";
    }

    @PostMapping("/updateUserById/{id}")
    //data save
    public String updateUserInfo(@PathVariable("id") Long userId,@ModelAttribute("user") UserDto userDto) throws Exception {
        // Perform the user update operation here
        // You can use the userService to update the user's information

        System.out.println("in updateuser info");
        System.out.println(userDto);
        userService.updateUser(userId,userDto);

        // Redirect to a suitable page after updating the user
        return "redirect:/admin/manage/manageUser";
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("queryType") String queryType,
                              @RequestParam("queryWord") String query,
                              Model model) {

        List<User> users = new ArrayList<>();

        if("firstName".equals(queryType)){
            users = userRepository.findByFirstNameContaining(query);
        } else if ("lastName".equals(queryType)) {
            users = userRepository.findByLastNameContaining(query);
        } else if ("email".equals(queryType)) {
            users = userRepository.findByEmailContaining(query);
        }

        for (User user : users) {
            System.out.println("User Found: " + user.getFirstName() + " " + user.getLastName() + " - " + user.getEmail());
        }
        if(users.isEmpty()){
            return "no_user_found";
        }
        System.out.println("printing user ," + users);
        model.addAttribute("listSearchUsers",users);
        return "admin/user/SearchManagerUser";

    }




}


