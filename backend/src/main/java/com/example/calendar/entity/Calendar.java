package com.example.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "calendar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "user_id", nullable = false)
    private Long userId; // 추후 Account 엔티티 연동 시 ManyToOne로 변환 예정
}
