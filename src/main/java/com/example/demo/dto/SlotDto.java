package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SlotDto {
    private LocalDateTime startTime;
    private Boolean isAvailable;
    private Boolean isReserved;
    private String summary;
    private String detail;
}
