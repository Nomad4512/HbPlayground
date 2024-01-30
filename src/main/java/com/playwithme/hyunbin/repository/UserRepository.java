package com.playwithme.hyunbin.repository;

import com.playwithme.hyunbin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {

    // 회원가입 이메일 중복 검사
    boolean existsByUserEmail(String userEmail);

    // 회원가입 닉네임 중복 검사
    boolean existsByUserName(String userName);
}
