package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.User;
import com.example.demo.repository.SlotTemplateRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class StudentTimelineController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SlotTemplateRepository slotTemplateRepository;
    
    @GetMapping("/student/timeline")
    public String showStudentTimeline(Model model, Authentication auth, @RequestParam(required = false) String dateString) {
        // dateStringからLocalDateへ変換
        LocalDate date = null;
        if (dateString != null && !dateString.isEmpty()) {
            date = LocalDate.parse(dateString);
        }
        if (date == null) {
            date = LocalDate.now();
        } 
        
        // 今日以前を選択できないように
        LocalDate today = LocalDate.now();
        if (date.isBefore(today)) {
            date = today;
        }

    	String userId = auth.getName();
    	Optional<User> user = userRepository.findByUserId(userId);
        String userName = user.map(User::getName).orElse("Unknown Name");
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        model.addAttribute("date", date);
        
        List<SlotTemplate> slotTemplates = slotTemplateRepository.findAll();
        model.addAttribute("SlotTemplates", slotTemplates);
        
        List<User> counselors = userRepository.findByRole();
        // TODO:NOW(2026-08-15) その日のタイムラインを表示するのを実装する
        // TimelineServiceを作って、予約情報を取得するようにする
        // また、TimelineDtoを作って、それをhtmlに渡すようにする
        // TimelineDtoでは、空きコマもちゃんと持つ形にする
        return "student/timeline"; 
    }
}
