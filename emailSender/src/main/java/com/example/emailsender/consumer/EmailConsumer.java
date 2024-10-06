package com.example.emailsender.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {
    @KafkaListener(topics = "user-created", groupId = "myGroup")
    public void consume(String message) {
        log.info("Consumed message: {}", message);
        System.out.printf("New user with email: %s was created%n", message);
    }
}
