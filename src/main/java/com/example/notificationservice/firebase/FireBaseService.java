package com.example.notificationservice.firebase;

import com.example.notificationservice.dto.RabbitDTO;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FireBaseService {

    private final FirebaseMessaging firebaseMessaging;

    public FireBaseService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(RabbitDTO dto, String token) throws FirebaseMessagingException {

        System.out.println("helloooo..............");
        Notification notification = Notification
                .builder()
                .setTitle("note.getSubject()")
                .setBody("note.getContent()")
                .build();

        Map<String, String> data = new HashMap<>();

        data.put("userId", dto.getUserId().toString());
        data.put("tenderId", dto.getTenderId().toString());
        data.put("totalRate", dto.getTotalRate().toString());
        data.put("submissionDateTime", dto.getSubmissionDateTime());

        Message message = Message
                .builder()
                .setTopic("yuqol")
//                .setToken(token)
                .setNotification(notification)
                .putAllData(data)
                .build();

        return firebaseMessaging.send(message);
    }

}