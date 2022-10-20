package com.example.notificationservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RabbitDTO {

    private BigDecimal totalRate;

    private Long userId;

    private Long tenderId;

    private String submissionDateTime;

}

