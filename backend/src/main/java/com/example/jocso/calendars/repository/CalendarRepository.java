package com.example.jocso.calendars.repository;

import com.example.jocso.calendars.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    // 사용자 ID로 일정 목록 조회
    List<Calendar> findByUserId(Long userId);
}
