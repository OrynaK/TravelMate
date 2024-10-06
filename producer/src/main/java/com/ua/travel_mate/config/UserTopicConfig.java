package com.ua.travel_mate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class UserTopicConfig {

    @Bean
    public NewTopic userCreatedTopic() {
        return TopicBuilder.name("user-created").build();
    }
}
