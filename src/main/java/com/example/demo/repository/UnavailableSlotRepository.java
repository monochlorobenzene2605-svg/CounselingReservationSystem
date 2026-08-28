package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SlotTemplate;
import com.example.demo.entity.UnavailableSlot;
import com.example.demo.entity.User;

public interface UnavailableSlotRepository extends JpaRepository<UnavailableSlot, Integer> {
    List<UnavailableSlot> findByCounselorAndDate(User counselor, LocalDate date);
    boolean existsByCounselorAndDateAndSlotTemplate(User counselor, LocalDate date, SlotTemplate slot);
}
