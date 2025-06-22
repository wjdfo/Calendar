package com.example.calendar.dto;
// 일정생성해서 보낼때 응답받기
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarRequestDto {
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
