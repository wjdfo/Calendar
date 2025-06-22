package com.example.jocso.calendars.controller;

import com.example.jocso.calendars.dto.CalendarRequestDto;
import com.example.jocso.calendars.dto.CalendarResponseDto;
import com.example.jocso.calendars.service.CalendarService;
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

    //일정 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<CalendarResponseDto> updateCalendar(
            @PathVariable Long id,
            @RequestBody CalendarRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String token) {

        Long userId = 1L; // 임시 하드코딩
        CalendarResponseDto response = calendarService.updateCalendar(id, requestDto, userId);
        return ResponseEntity.ok(response);
    }

    //일정 삭제 기능
    @DeleteMapping("/{calendarId}")
    public ResponseEntity<String> deleteCalendar(
            @PathVariable Long calendarId,
            @RequestHeader(value = "Authorization", required = false) String token) {

        Long userId = 1L; // 인증 구현 전까지는 하드코딩
        calendarService.deleteCalendar(calendarId, userId);
        return ResponseEntity.ok("정상적으로 삭제되었습니다.");
        // return ResponseEntity.noContent().build(); // 204 No Content
    }
}
