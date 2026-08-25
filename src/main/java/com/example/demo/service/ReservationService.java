package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.User;
import com.example.demo.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public boolean reserve(User student, User counselor, LocalDate date, SlotTemplate slot, String summary, String detail){
        try {
            Reservation reservation = new Reservation(student, counselor, date, slot, summary, detail);
            reservationRepository.save(reservation);
            // TODO: 自分がすでに同じ時間の別の相談員に予約している場合の処理について
        } catch (Exception e) { 
            System.out.println(e.getStackTrace());
        }

        return true;
    }
}
