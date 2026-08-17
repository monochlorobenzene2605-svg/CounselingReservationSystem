package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.UnavailableSlot;

public interface UnavailableSlotRepository extends JpaRepository<UnavailableSlot, Integer> {
    List<UnavailableSlot> findByDate(LocalDate date);
}
