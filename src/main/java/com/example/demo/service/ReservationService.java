package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.UnavailableSlot;
import com.example.demo.entity.User;
import com.example.demo.repository.ReservationRepository;
import com.example.demo.repository.SlotTemplateRepository;
import com.example.demo.repository.UnavailableSlotRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UnavailableSlotRepository unavailableSlotRepository;
    @Autowired
    private SlotTemplateRepository slotTemplateRepository;

    public boolean reserve(User student, User counselor, LocalDate date, SlotTemplate slot, String summary,
            String detail) {
        if (isExistsUnavailable(counselor, date, slot)) {
            throw new IllegalArgumentException("その時間は面談不可です。");
        }

        Reservation reservation = new Reservation(student, counselor, date, slot, summary, detail);
        try {
            reservationRepository.save(reservation);
        } catch (DataIntegrityViolationException e) {
            throw e; // すでに同じ時間帯の他の相談員に予約している場合
            // この理由で予約不可の場合はslotを「予約済みです」とする案も考えたが、実装が煩雑になるため今回はシンプルにエラーとした。
            // TODO:feat 将来的に上の案で書き直したい
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            return false;
        }
        return true;
    }

    public boolean cancel(int reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            return false;
        }
        try {
            reservationRepository.deleteById(reservationId);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    private boolean isExistsUnavailable(User counselor, LocalDate date, SlotTemplate slot) {
        return unavailableSlotRepository.existsByCounselorAndDateAndSlotTemplate(counselor, date, slot);
    }

    public boolean registerUnavailable(User counselor, LocalDate date, LocalTime time) {
        SlotTemplate slotTemplate = slotTemplateRepository.findByStartTime(time).orElseThrow();
        UnavailableSlot unavailableSlot = new UnavailableSlot(counselor, date, slotTemplate);
        try {
            unavailableSlotRepository.save(unavailableSlot);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public boolean cancelUnavailable(LocalDate date, LocalTime time) {
        return true;
    }
}
