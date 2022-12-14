package com.example.notificationservice.controller;

import com.example.notificationservice.dto.NotificationClickDTO;
import com.example.notificationservice.dto.NotificationDTO;
import com.example.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/notification")
@CrossOrigin("*")
public class NotificationController {


    private final NotificationService service;

    @Autowired
    public NotificationController(NotificationService service) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAll() {
        return service.getAll();
    }


    @PostMapping("/on_click")
    public ResponseEntity<Void> onClick(@RequestBody NotificationClickDTO dto) {
        return service.onClick(dto);
    }


    @GetMapping("/{tenderId}/{bidderId}")
    public ResponseEntity<NotificationDTO> get(@PathVariable Long tenderId, @PathVariable Long bidderId) {
        return service.findById(tenderId, bidderId);
    }


}
