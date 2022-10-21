package com.example.notificationservice.repository;

import com.example.notificationservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    Optional<Notification> findByBidderIdAndTenderIdAndClickIsFalse(Long bidderId, Long tenderId);

}
