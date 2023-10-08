package com.ecomapp.ecomapp.repository;

import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    List<User> findByFirstNameContaining(String firstName);
    List<User> findByLastNameContaining(String lastName);
    List<User> findByEmailContaining(String email);

    public User findByResetPasswordToken(String token);
}

