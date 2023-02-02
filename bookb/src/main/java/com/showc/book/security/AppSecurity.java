package com.showc.book.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@EnableWebSecurity
public class AppSecurity {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().and().csrf().disable()
                .authorizeRequests()
                .requestMatchers("/bookapp/v1/getallbook",
                        "/bookapp/v1/getbook/**",
                        "/bookapp/v1/addbook",
                        "/bookapp/v1/updatebook",
                        "/bookapp/v1/delete/**",
                        "/bookapp/v1/assignstore",
                        "/bookapp/v1/uploadimage").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        return httpSecurity.build();
    }
}
