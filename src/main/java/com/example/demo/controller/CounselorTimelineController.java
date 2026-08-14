package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class CounselorTimelineController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/counselor/timeline")
    public String showCounselorTimeline(Model model, Authentication auth) {
    	String userId = auth.getName();
    	Optional<User> user = userRepository.findByUserId(userId);
        String userName = user.map(User::getName).orElse("Unknown Name");
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        return "counselor/timeline"; 
    }
}
