package com.example.demo.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Controller
public class CounselorTimelineController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/counselor/timeline")
    public String showCounselorTimeline(Model model, Authentication auth, @RequestParam(required = false) String date) {
        if (date == null || date.isEmpty()) {
            date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    	String userId = auth.getName();
    	Optional<User> user = userRepository.findByUserId(userId);
        String userName = user.map(User::getName).orElse("Unknown Name");
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("date", date);

        return "counselor/timeline"; 
    }
}
