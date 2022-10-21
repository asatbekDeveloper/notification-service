package com.example.notificationservice.service;


import com.example.notificationservice.dto.NotificationClickDTO;
import com.example.notificationservice.dto.NotificationDTO;
import com.example.notificationservice.dto.RabbitDTO;
import com.example.notificationservice.entity.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationService(NotificationRepository repository, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void receive(RabbitDTO dto) {
        sendNotification(dto);
    }

    @SneakyThrows
    public void sendNotification(RabbitDTO dto) {
        messagingTemplate.convertAndSend("/topic/notifications", new NotificationDTO(dto.getTotalRate(), dto.getUserId(), dto.getTenderId(), false, dto.getSubmissionDateTime()));

        saveNotification(dto);
    }

    private void saveNotification(RabbitDTO dto) {

        Optional<Notification> optionalNotification = repository.findByTenderIdAndBidderId(dto.getTenderId(), dto.getUserId());
        if (optionalNotification.isEmpty()) {
            repository.save(new Notification(dto.getUserId(), dto.getTenderId(), dto.getTotalRate(), false, dto.getSubmissionDateTime()));
        }
    }

    public ResponseEntity<List<NotificationDTO>> getAll() {

        List<Notification> notifications = repository.findAll(Sort.by(Sort.Direction.DESC, "submissionDateTime"));

        return new ResponseEntity<>(toDTO(notifications), HttpStatus.OK);
    }

    private List<NotificationDTO> toDTO(List<Notification> notifications) {

        return notifications.stream().map(notification -> NotificationDTO.builder()
                .bidderId(notification.getBidderId())
                .tenderId(notification.getTenderId())
                .totalRate(notification.getTotalRate())
                .click(notification.isClick())
                .submissionDateTime(notification.getSubmissionDateTime())
                .build()).collect(Collectors.toList());

    }


    private NotificationDTO toDTO(Notification notification) {

        return NotificationDTO.builder()
                .bidderId(notification.getBidderId())
                .tenderId(notification.getTenderId())
                .totalRate(notification.getTotalRate())
                .click(notification.isClick())
                .submissionDateTime(notification.getSubmissionDateTime())
                .build();

    }


    public ResponseEntity<Void> onClick(NotificationClickDTO dto) {
        Optional<Notification> optionalNotification = repository.findByBidderIdAndTenderIdAndClickIsFalse(dto.getBidderId(), dto.getTenderId());

        if (optionalNotification.isEmpty()) throw new RuntimeException("Notification not found");

        Notification notification = optionalNotification.get();
        notification.setClick(true);
        repository.save(notification);
        messagingTemplate.convertAndSend("/topic/notifications", 1);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<NotificationDTO> findById(Long tenderId, Long bidderId) {

        Optional<Notification> optionalNotification = repository.findByTenderIdAndBidderId(tenderId, bidderId);

        if (optionalNotification.isEmpty()) throw new RuntimeException("Notification not found");

        return new ResponseEntity<>(toDTO(optionalNotification.get()), HttpStatus.OK);
    }
}
