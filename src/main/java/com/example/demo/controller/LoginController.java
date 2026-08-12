package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model) { 
        return "login";
    }
    
    @PostMapping("/login")
    public String processLogin(@RequestParam("userid") String userid, @RequestParam("password") String password, HttpSession session) {
        if (!userService.login(userid, password)) { // ログイン失敗時早期return
            return "redirect:/login?error";
        }

        User user = userRepository
                .findByUserId(userid)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); // ログインは成功しているはずなのでここでuserが見つからないのはおかしい

        session.setAttribute("userId", userid);

        String today = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return (switch (user.getRole()) {
            case User.Role.STUDENT -> "redirect:/student/timeline?date=" + today;
            case User.Role.COUNSELOR -> "redirect:/counselor/timeline?date=" + today;
        });
    }
}
