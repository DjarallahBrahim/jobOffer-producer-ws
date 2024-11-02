package com.kafka.webservice.producer.services;

import com.joboffer.ws.core.JobOfferCreatedEvent;
import com.kafka.webservice.producer.models.JobOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class JobOfferServiceImpl implements JobOfferService{

    private final Logger  LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${topic.kafka.name}")
    private String kafkaTopicName;
    KafkaTemplate<String, JobOfferCreatedEvent> kafkaTemplate;

    public JobOfferServiceImpl(KafkaTemplate<String, JobOfferCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createJobOffer(JobOffer jobOffer) throws Exception{
        LOGGER.info("[JobOfferServiceImpl] creating new JobOffer=[{}]", jobOffer);

        String jobOfferId = UUID.randomUUID().toString();
        //TODO save offer in databases;

        JobOfferCreatedEvent jobOfferCreatedEvent = new JobOfferCreatedEvent(jobOfferId,
                jobOffer.getTitle(),jobOffer.getDescription(), jobOffer.getSalary());

//        CompletableFuture<SendResult<String, JobOfferCreatedEvent>> future =
//                kafkaTemplate.send(this.kafkaTopicName, jobOfferId, jobOfferCreatedEvent);
//
//        future.whenComplete((result, exception) -> {
//           if(exception != null){
//               LOGGER.error("[JobOfferServiceImpl] Faild to send message:" + exception.getMessage());
//           }else{
//               LOGGER.info("[JobOfferServiceImpl] Message send successfully: " + result.getRecordMetadata());
//           }
//        });
//
//        future.join();

        SendResult<String, JobOfferCreatedEvent> result =
                kafkaTemplate.send(this.kafkaTopicName, jobOfferId, jobOfferCreatedEvent).get();
        LOGGER.info("[JobOfferServiceImpl] Topic Partitions=[{}]", result.getRecordMetadata().partition());
        LOGGER.info("[JobOfferServiceImpl] Topic name=[{}]", result.getRecordMetadata().topic());
        LOGGER.info("[JobOfferServiceImpl] Topic offset=[{}]", result.getRecordMetadata().offset());

        return jobOfferId;
    }
}
