package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService
        implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(userId));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserId())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
