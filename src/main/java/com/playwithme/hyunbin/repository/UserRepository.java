package com.playwithme.hyunbin.repository;

import com.playwithme.hyunbin.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {

}
