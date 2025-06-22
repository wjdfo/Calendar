package com.example.calendar.calendars.controller;

import com.example.calendar.calendars.dto.CalendarRequestDto;
import com.example.calendar.calendars.dto.CalendarResponseDto;
import com.example.calendar.calendars.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/calendars")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    //일정 보기 기능
    @GetMapping
    public ResponseEntity<List<CalendarResponseDto>> getCalendars(
            @RequestHeader(value = "Authorization", required = false) String token) {
        // 추후 토큰 파싱해서 userId 추출해야 함 (임시로 userId 하드코딩)
        Long userId = 1L;
        List<CalendarResponseDto> calendars = calendarService.getCalendarsByUserId(userId);
        return ResponseEntity.ok(calendars);
    }

    //일정 생성 기능
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
