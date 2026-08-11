package com.example.demo.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;

@Entity
public class SlotTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    // 値が変更されることはないので、setterは作らない
    @Column(name = "period_no", nullable = false, unique = true)
    @Getter
	private String periodNo; // n限目
    
    @Column(name = "start_time", nullable = false)
    @Getter
    private LocalTime startTime; // 開始時間
    
    @Column(name = "end_time", nullable = false)
    @Getter
    private LocalTime endTime; // 終了時間
}
