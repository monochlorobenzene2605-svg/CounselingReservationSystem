package com.example.demo.controller;

import java.util.Optional;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

// TODO: 未ログイン時にログインページにリダイレクトするようにする
@Controller
public class StudentTimelineController {
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/student/timeline")
    public String showStudentTimeline(Model model, HttpSession session) {
    	String userId = (String) session.getAttribute("userId");
    	Optional<User> user = userRepository.findByUserId(userId);
        String userName = user.map(User::getName).orElse("Unknown Name");
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        return "student/timeline"; 
    }
}
