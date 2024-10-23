package com.example.emailsender.consumer;

import com.example.emailsender.messages.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailConsumer {
    private final KafkaTemplate<String, UserMessage> kafkaTemplate;

    public EmailConsumer(KafkaTemplate<String, UserMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "user-created", groupId = "myGroup2")
    public void consume(Message<UserMessage> message) {
        String flowId = message.getHeaders().get("InitialFlowId", String.class);

        log.info("FlowId: " + flowId + " Consumed message: {}", message.getPayload());
        System.out.printf("New user with email: %s was created%n", message.getPayload());
    }
}
