package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    // 今回は値を変更することは考えないので、setterは作らない
    @Column(name = "user_id", nullable = false, unique = true,columnDefinition = "VARCHAR(64) COLLATE utf8mb4_bin")
    @Getter
	private String userId; // ログイン時に使用するユーザーID
    
    @Column(name = "password", nullable = false,columnDefinition = "VARCHAR(64) COLLATE utf8mb4_bin")
    @Getter
    private String password; // ログイン時に使用するパスワード
    
    @Column(name = "name", nullable = false)
    @Getter
    private String name; // ユーザーの名前

    public enum Role {
        COUNSELOR, STUDENT
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Getter
    private Role role = Role.STUDENT; // デフォルトはSTUDENT
	
}
