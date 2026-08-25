package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
        } catch (DataIntegrityViolationException e){
            throw e; // すでに同じ時間帯の他の相談員に予約している場合
            // この理由で予約不可の場合はslotを「予約済みです」とする案も考えたが、実装が煩雑になるため今回はシンプルにエラーとした。
            // TODO:feat 将来的に上の案で書き直したい
        } catch (Exception e) { 
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }
    
    public boolean cancel(int reservationId){
        if(!reservationRepository.existsById(reservationId)) {
            return false;
        }
        try {
            reservationRepository.deleteById(reservationId);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }
}
