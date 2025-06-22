package com.example.calendar.repository;

import com.example.calendar.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}
