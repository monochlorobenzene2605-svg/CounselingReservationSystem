package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationListController {
    @GetMapping("/student/reservation-list")
    public String reservationList(@RequestParam(required = false) String dateTimeString){
        return ("student/reservation-list");
    }
}
