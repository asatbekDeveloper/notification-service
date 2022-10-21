package com.example.notificationservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NotificationDTO {


    private BigDecimal totalRate;

    private Long bidderId;

    private Long tenderId;

    private boolean click;

    private String submissionDateTime;

}
