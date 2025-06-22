package com.example.calendar.controller;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @PostMapping
    public ResponseEntity<CalendarResponseDto> createCalendar(
            @RequestBody CalendarRequestDto requestDto,
            // 일단 인증은 비활성화
            @RequestHeader(value = "Authorization", required = false) String token) {

        Long userId = 1L; // 로그인 기능 구현 전, 임시 유저 ID
        CalendarResponseDto response = calendarService.createCalendar(requestDto, userId);
        return ResponseEntity.status(201).body(response);
    }
}
