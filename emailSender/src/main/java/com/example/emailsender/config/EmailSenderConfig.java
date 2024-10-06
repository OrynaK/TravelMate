package com.example.emailsender.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class EmailSenderConfig {
    @Bean
    public NewTopic userCreatedTopic() {
        return TopicBuilder.name("user-created").build();
    }
}
