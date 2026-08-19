package com.example.demo.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TimelineDto {
    private LocalDate date;
    private String counselorName;
    private List<SlotDto> slots = new ArrayList<>();

    public void addSlotDto(SlotDto slot) {
        slots.add(slot);
    }
}
