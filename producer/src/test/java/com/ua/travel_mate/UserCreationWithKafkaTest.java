package com.ua.travel_mate;

import com.ua.travel_mate.containers.MySQLTestContainer;
import com.ua.travel_mate.entities.Users;
import com.ua.travel_mate.testData.UsersTestData;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringJUnitConfig
@EmbeddedKafka(partitions = 1, topics = {"user-created"})
public class UserCreationWithKafkaTest extends MySQLTestContainer {

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    public void testCreateUser_andSendKafkaMessage() throws Exception {
        Users user = UsersTestData.getUser().username("Tad").email("tad@example.com").build();
        String createUrl = "http://localhost:" + port + "/api/users";
        ResponseEntity<Users> response = restTemplate.postForEntity(createUrl, user, Users.class);

        assertThat(response.getStatusCode()).isEqualTo(CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("myGroup", "false", embeddedKafkaBroker);
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        Consumer<Object, Object> consumer = new DefaultKafkaConsumerFactory<>(consumerProps).createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, "user-created");

        ConsumerRecord<Object, Object> received = KafkaTestUtils.getSingleRecord(consumer, "user-created");
        assertThat(received.value()).isEqualTo("tad@example.com");
    }
}
