package com.ecomapp.ecomapp.service.ProfileService;

import com.ecomapp.ecomapp.model.User;

public interface ProfileService {

    User findByEmail(String email);

}
