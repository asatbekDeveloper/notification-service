package com.example.notificationservice.service;


import com.example.notificationservice.dto.RabbitDTO;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(RabbitDTO dto) {
        sendNotification(dto);
    }

    @SneakyThrows
    public void sendNotification(RabbitDTO dto) {

    }
}
