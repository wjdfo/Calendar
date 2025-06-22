package com.example.calendar.calendars.dto;
// 요청한 일정에 대한 응답보내기
import lombok.Builder;
import lombok.Getter;
import com.example.calendar.calendars.entity.Calendar;
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

    // 일정 보기 기능을 위한 엔터티
    public static CalendarResponseDto fromEntity(Calendar calendar) {
        return CalendarResponseDto.builder()
                .id(calendar.getId())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .startDate(calendar.getStartDate())
                .endDate(calendar.getEndDate())
                .userId(calendar.getUserId())
                .build();
    }
}
