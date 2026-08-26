package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.SlotDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ScheduleService;

@Controller
public class ReservationListController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/student/reservation-list")
    public String reservationList(Model model, Authentication auth) {
        String userId = auth.getName();
        Optional<User> student = userRepository.findByUserId(userId);

        List<SlotDto> slots = new ArrayList<>();
        slots = scheduleService.createReservationSlots(student.orElseThrow());

        model.addAttribute("slots",slots);
        return ("student/reservation-list");
    }
}
