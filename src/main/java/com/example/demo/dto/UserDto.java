package com.example.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserDto {
    private final int id; // DBのインデックスid 登録処理用に持っておく
    private final String name;
    enum Role {
        STUDENT, COUNSELOR
    }
    private final Role role;
}
