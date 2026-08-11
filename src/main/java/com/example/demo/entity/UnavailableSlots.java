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
    name = "unavailable_slots",
    uniqueConstraints = {
        @UniqueConstraint{
            columnNames = {"counselor_id", "date", "slot_template_id"}
        }
    }
)
public class UnavailableSlots {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counselor_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private User counselor; // カウンセラー
    
    @Column(name = "date", nullable = false)
    @Getter @Setter
    private LocalDate date; // 日付

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_template_id", referencedColumnName = "id", nullable = false)
    @Getter @Setter
    private SlotTemplate slotTemplate; // コマ

    public Reservation(
            User counselor,
            LocalDate date,
            SlotTemplate slotTemplate,
    ) {
        this.counselor = counselor;
        this.date = date;
        this.slotTemplate = slotTemplate;
    }
} 
