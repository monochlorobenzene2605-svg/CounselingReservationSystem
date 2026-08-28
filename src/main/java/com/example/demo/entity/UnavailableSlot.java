package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "unavailable_slot",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"counselor_id", "date", "slot_template_id"}
        )
    }
)
public class UnavailableSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
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

    public UnavailableSlot(
            User counselor,
            LocalDate date,
            SlotTemplate slotTemplate
    ) {
        this.counselor = counselor;
        this.date = date;
        this.slotTemplate = slotTemplate;
    }
} 
