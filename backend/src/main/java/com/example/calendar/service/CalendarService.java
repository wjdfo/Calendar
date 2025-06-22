package com.example.calendar.service;

import com.example.calendar.dto.CalendarRequestDto;
import com.example.calendar.dto.CalendarResponseDto;
import com.example.calendar.entity.Calendar;
import com.example.calendar.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public CalendarResponseDto createCalendar(CalendarRequestDto requestDto, Long userId) {
        Calendar calendar = Calendar.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .userId(userId)
                .build();

        Calendar saved = calendarRepository.save(calendar);

        return CalendarResponseDto.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .content(saved.getContent())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .userId(saved.getUserId())
                .build();
    }
}
