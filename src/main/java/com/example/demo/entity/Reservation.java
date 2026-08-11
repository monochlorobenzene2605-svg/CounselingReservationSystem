package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(
    name = "reservations",
    uniqueConstraints = {
        @UniqueConstraint{
            name = "unique_reservation_student",
            columnNames = {"student_id", "date", "slot_template_id"}
        },
        @UniqueConstraint{
            name = "unique_reservation_counselor",
            columnNames = {"counselor_id", "date", "slot_template_id"}
        }
    }
)
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private User student; // 受講生

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselor_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private User counselor; // カウンセラー

    @Column(name = "date", nullable = false)
    @Getter @Setter
    private LocalDate date; // 予約日付

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_template_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private SlotTemplate slotTemplate; // コマ

    @Column(name = "summary", nullable = false, length = 60)
    @Getter @Setter
    private String summary; // 相談内容の概要
    
    @Column(name = "details", columnDefinition = "TEXT")
    @Getter @Setter
    private String details; // 相談内容の詳細

    public Reservation(
            User student,
            User counselor,
            LocalDate date,
            SlotTemplate slotTemplate,
            String summary
    ) {
        this.student = student;
        this.counselor = counselor;
        this.date = date;
        this.slotTemplate = slotTemplate;
        this.summary = summary;
    }
    public Reservation(
            User student,
            User counselor,
            LocalDate date,
            SlotTemplate slotTemplate,
            String summary,
            String details
    ) {
        this(student, counselor, date, slotTemplate, summary);
        this.details = details;
    }
}
