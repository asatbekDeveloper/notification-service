package com.example.notificationservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bidderId;

    private Long tenderId;

    private BigDecimal totalRate;

    @Column(name = "is_click")
    private boolean click;

    private String submissionDateTime;

    public Notification(Long bidderId, Long tenderId, BigDecimal totalRate, boolean click, String submissionDateTime) {
        this.bidderId = bidderId;
        this.tenderId = tenderId;
        this.totalRate = totalRate;
        this.click = click;
        this.submissionDateTime = submissionDateTime;
    }
}
