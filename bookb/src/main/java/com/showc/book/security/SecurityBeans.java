package com.showc.book.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityBeans {
    @Value("${spring.security.user.username}")
    private String adminusername;
    @Value("${spring.security.user.password}")
    private String adminpw;
    @Value("${spring.security.user.username2}")
    private String adminusername2;
    @Value("${spring.security.user.password2}")
    private String adminpw2;
    @Value("${spring.security.user.roles}")
    private String adminroles;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    protected UserDetailsService userDetailsService(){
        UserDetails admin = User.builder().username(adminusername)
                .password(passwordEncoder().encode(adminpw)).roles(adminroles).build();
        UserDetails admin2 = User.builder().username(adminusername2)
                .password(passwordEncoder().encode(adminpw2)).roles(adminroles).build();
        return new InMemoryUserDetailsManager(admin, admin2);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
                                                       PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }
}
