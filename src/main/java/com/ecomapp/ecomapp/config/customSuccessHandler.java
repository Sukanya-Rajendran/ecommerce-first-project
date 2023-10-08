package com.ecomapp.ecomapp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
public class customSuccessHandler implements AuthenticationSuccessHandler {





    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Set<String> roleSet= AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if(roleSet.contains("ROLE_ADMIN"))
        {
            response.sendRedirect("/admin/adminMainPage");
        }
        else
        {		//setting session for change the login logout in navbar


            response.sendRedirect("/");
        }
    }
}