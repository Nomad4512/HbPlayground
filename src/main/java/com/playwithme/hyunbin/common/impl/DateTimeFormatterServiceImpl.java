package com.playwithme.hyunbin.common.impl;

import com.playwithme.hyunbin.common.DateTimeFormatterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class DateTimeFormatterServiceImpl implements DateTimeFormatterService {

    // 현재날짜 시간 포매팅 추상화 구현
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    @Override
    public String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(FORMATTER);
    }
}
