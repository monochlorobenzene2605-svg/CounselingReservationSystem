package com.example.demo.dto;

import java.time.LocalTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SlotDto {
    private final LocalTime startTime;
    public enum Status {
        Empty, Reserved, Mine, Unavailabled
    }
    private Status status = Status.Empty;
    private String summary = "summary";
    private String detail = "detail";
    private int reservationId;
}
