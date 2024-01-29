package com.playwithme.hyunbin.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_pw1")
    private String userPw1;

    @Column(name = "user_pw2")
    private String userPw2;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

}
