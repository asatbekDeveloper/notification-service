package com.example.notificationservice.service;


import com.example.notificationservice.dto.RabbitDTO;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {


    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(RabbitDTO dto) {
        sendNotification(dto);
    }


    @SneakyThrows
    public void sendNotification(RabbitDTO dto) {

        messagingTemplate.convertAndSend("/topic/notification",dto);

    }
}
