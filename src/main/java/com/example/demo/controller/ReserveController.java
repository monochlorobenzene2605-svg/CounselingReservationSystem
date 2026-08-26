package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.User;
import com.example.demo.repository.SlotTemplateRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ReservationService;

@Controller
public class ReserveController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SlotTemplateRepository slotRepository;
    @Autowired
    private ReservationService reservationService;

    @PostMapping("/reserve")
    public String reserve(@RequestParam String counselorId, @RequestParam String dateTime, @RequestParam String summary,
            @RequestParam String detail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUserId = auth.getName();
        User student = userRepository.findByUserId(loginUserId).orElseThrow();

        User counselor = userRepository.findById(Integer.parseInt(counselorId)).orElseThrow();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse(dateTime, formatter);
        LocalDate date = ldt.toLocalDate();

        LocalTime time = ldt.toLocalTime();
        SlotTemplate slot = slotRepository.findByStartTime(time).orElseThrow();

        // TODO: リダイレクト先を予約した日時に
        String today = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            reservationService.reserve(student, counselor, date, slot, summary, detail);
        } catch (DataIntegrityViolationException e) { // すでに同じ時間の別な相談員に予約している場合
            return ("redirect:/student/timeline?date=" + today + "&status=alreadyReserved");
        }
        return ("redirect:/student/timeline?date=" + today + "&status=reserved");
    }

    @PostMapping("/reserve/cancel")
    public String cancel(@RequestParam int reservationId,
            @RequestHeader(value = "Referer", required = true) String referer) {
        boolean isCanceled = reservationService.cancel(reservationId);

        String status = "canceled";
        if (!isCanceled) {
            status = "cancelError";
        }

        String redirectUrl = UriComponentsBuilder
                .fromUriString(referer)
                .queryParam("status", status)
                .build()
                .toUriString();

        return "redirect:" + redirectUrl;
    }
}
