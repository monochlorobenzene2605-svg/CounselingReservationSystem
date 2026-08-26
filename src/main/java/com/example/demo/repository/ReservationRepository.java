package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.User;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByCounselorAndDate(User counselor, LocalDate date);
    List<Reservation> findByStudent(User student);
}
