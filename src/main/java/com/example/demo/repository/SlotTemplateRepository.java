package com.example.demo.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.SlotTemplate;

public interface SlotTemplateRepository extends JpaRepository<SlotTemplate, Integer> {
    Optional<SlotTemplate> findByStartTime(LocalTime startTime);
}
