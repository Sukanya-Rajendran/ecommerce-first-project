package com.ecomapp.ecomapp.repository.ProfileRepo;

import com.ecomapp.ecomapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<User,Long> {
// User findByUsername(String username);
    User findByEmail(String email);

}
