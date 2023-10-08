package com.ecomapp.ecomapp.config;

import com.ecomapp.ecomapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    customSuccessHandler customSuccessHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().enableSessionUrlRewriting(true);
        http.authorizeRequests()
                .antMatchers(
                        "/registration/**",
                        "/js/**",
                        "/css/**","/assets/**",
                        "/otp/**",
                        "/img/**",
                        "/",
                        "/user/**",
                        "/image/**",
                        "/address/**"

                ).permitAll()
                .antMatchers("/").hasAnyRole("USER","ADMIN","ANONYMOUS")
                .antMatchers("/profile").hasAnyRole("USER","ANONYMOUS")
                .antMatchers("/profile/**").hasAnyRole("USER","ANONYMOUS")
          .antMatchers("/signup").hasAnyRole("USER","ADMIN","ANONYMOUS")
                .antMatchers("/access-denied").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/address").hasAnyRole("User")
                .antMatchers("/getOtpPage/**").hasAnyRole("ADMIN","USER","ANONYMOUS")
                .antMatchers("/forgot_password/**").hasAnyRole("ADMIN","USER","ANONYMOUS")
              .antMatchers("/product/**").hasAnyRole("ADMIN","USER")
              .antMatchers("/user","/user/**").hasRole("USER")
                .antMatchers("/reset_password/**").hasAnyRole("ADMIN","USER","ANONYMOUS")
                .antMatchers("/showProductInCart/**").hasAnyRole("ADMIN","USER","ANONYMOUS")
                .antMatchers("/all/**").hasAnyRole("ADMIN","USER","ANONYMOUS")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied"); // Specify the custom access denied page

    }

}
