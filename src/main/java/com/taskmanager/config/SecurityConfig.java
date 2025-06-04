package com.taskmanager.config;

import com.taskmanager.service.UserService;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/register").permitAll()
            .anyRequest().authenticated()
        )
        .formLogin(login -> login
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error")
            .permitAll()
        )
        .logout(logout -> logout.permitAll());
    return http.build();
}


    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserService userService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder())
                .and().build();
    }
}
