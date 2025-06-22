package com.example.calendar.dto;
// 요청한 일정에 대한 응답보내기
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CalendarResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long userId;
}
