package com.example.notificationservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationClickDTO {

    private Long bidderId;

    private Long tenderId;
}
