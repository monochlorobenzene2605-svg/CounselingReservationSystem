package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SlotDto {
    private final LocalDateTime startTime;
    public enum Status {
        Empty, Reserved, Mine, Unavailabled
    }
    private Status status = Status.Empty;
    private String summary = "summary";
    private String detail = "detail";
    private int reservationId;
    private String counselorName;
}
