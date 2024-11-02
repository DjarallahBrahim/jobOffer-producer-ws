package com.kafka.webservice.kafka_ws.kafka.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Map;


@Configuration
public class Config {

    @Value("${topic.kafka.name}")
    private String kafkaTopicName;
    @Bean
    NewTopic createTopic(){
       return TopicBuilder.name(this.kafkaTopicName)
                .partitions(3)
                .replicas(2)
                .config("min.insync.replicas", "2") // Correct way to add config
                .build();
    }
}
