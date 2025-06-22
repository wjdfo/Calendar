package com.example.calendar.calendars.service;

import com.example.calendar.calendars.dto.CalendarRequestDto;
import com.example.calendar.calendars.dto.CalendarResponseDto;
import com.example.calendar.calendars.entity.Calendar;
import com.example.calendar.calendars.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;

    // 일정 생성 메서드
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

    // 일정 목록 조회 메서드
    public List<CalendarResponseDto> getCalendarsByUserId(Long userId) {
        return calendarRepository.findByUserId(userId).stream()
                .map(CalendarResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 일정 수정 메서드
    @Transactional
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto requestDto, Long userId) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 일정이 존재하지 않습니다."));

        // 작성자 확인 (간단한 보안 처리)
        if (!calendar.getUserId().equals(userId)) {
            throw new SecurityException("수정 권한이 없습니다.");
        }

        // 필드 업데이트
        calendar.setTitle(requestDto.getTitle());
        calendar.setContent(requestDto.getContent());
        calendar.setStartDate(requestDto.getStartDate());
        calendar.setEndDate(requestDto.getEndDate());

        return CalendarResponseDto.fromEntity(calendar);
    }
}
