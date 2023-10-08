package com.ecomapp.ecomapp.service.ProfileService;

import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
