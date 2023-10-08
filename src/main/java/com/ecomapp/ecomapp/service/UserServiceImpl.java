package com.ecomapp.ecomapp.service;

import com.ecomapp.ecomapp.dto.UserDto;
import com.ecomapp.ecomapp.model.Role;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // This method is used by Spring Security to load a user by their username.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find a user by their email (username).
        User user = userRepository.findByEmail(username);
        if (user == null) {
            // If the user is not found, throw an exception.
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        // Map the user's roles to Spring Security authorities and create a UserDetails object.
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    // This method maps the user's roles to Spring Security authorities.
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    // This method is used to save a new user.
    @Override
    public User save(UserDto userDto) {
        // Create a new User object with the provided userDto and encode the password.
        User user = new User(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                Arrays.asList(new Role("ROLE_USER")), // Assign a default role to the user.
                false
        );

        // Save the user to the UserRepository.
        return userRepository.save(user);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    @Transactional
    public void updateUser(Long userId, UserDto userDto) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(()-> new Exception("not found"));
        System.out.println("in update user , user entity");
        System.out.println(userDto);
        System.out.println(user);
        if (user != null) {
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
        }
        System.out.println(user);
         userRepository.save(user);
    }

    @Override
    public void registerNewCustomer(UserDto userDto) {
       User newUser  = new User();
       newUser.setEmail(userDto.getEmail());
       newUser.setFirstName(userDto.getFirstName());
       newUser.setLastName(userDto.getLastName());
       newUser.setRoles(Arrays.asList(new Role("ROLE_USER")));

       newUser.setBlocked(userDto.isBlocked());
       newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
       newUser.setId(userDto.getId());

       userRepository.save(newUser);
        System.out.println("saved");

    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email);
        System.out.println(user+"----------------------------");
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("Could not find any customer with the email " + email);
        }
    }


    @Override
     public User getByResetPasswordToken(String token) {
         return userRepository.findByResetPasswordToken(token);
}



    @Override
    public void updatePassword( User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}


