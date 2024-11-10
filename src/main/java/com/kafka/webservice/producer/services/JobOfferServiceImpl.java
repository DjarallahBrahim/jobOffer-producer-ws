package com.kafka.webservice.producer.services;

import com.joboffer.ws.core.JobOfferCreatedEvent;
import com.joboffer.ws.core.jpa.entities.JobOffer;
import com.kafka.webservice.producer.repository.JobOfferRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class JobOfferServiceImpl implements JobOfferService{

    private final Logger  LOGGER = LoggerFactory.getLogger(this.getClass());

    @Value("${topic.kafka.name}")
    private String kafkaTopicName;

    private final JobOfferRepository jobOfferRepository;
    KafkaTemplate<String, JobOfferCreatedEvent> kafkaTemplate;

    public JobOfferServiceImpl(KafkaTemplate<String, JobOfferCreatedEvent> kafkaTemplate, JobOfferRepository jobOfferRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.jobOfferRepository = jobOfferRepository;
    }

    @Override
    public JobOffer saveJobOfferToBDD(JobOffer jobOffer) {
        return jobOfferRepository.save(jobOffer);
    }

    @Override
    public String savJobOfferToBddKafka(JobOffer jobOffer) throws Exception{
        LOGGER.info("[JobOfferServiceImpl] creating new JobOffer=[{}]", jobOffer);

        String jobOfferId = jobOffer.getId();
        saveJobOfferToBDD(jobOffer);
        sendEventToKafka(jobOffer);
        return jobOfferId;
    }

    @Override
    public void sendEventToKafka(JobOffer jobOffer) throws InterruptedException, ExecutionException {
        String jobOfferId = jobOffer.getId();

        JobOfferCreatedEvent jobOfferCreatedEvent = new JobOfferCreatedEvent(jobOffer.getId(), jobOffer.getName(),
                jobOffer.getSkills(), jobOffer.getDescription(), jobOffer.getEmail(), jobOffer.getSalary());

        ProducerRecord<String, JobOfferCreatedEvent> record = new ProducerRecord<>(this.kafkaTopicName, jobOfferId, jobOfferCreatedEvent);
        record.headers().add("messageId", jobOfferId.getBytes());
        SendResult<String, JobOfferCreatedEvent> result =
                kafkaTemplate.send(record).get();
        LOGGER.info("[JobOfferServiceImpl] Topic Partitions=[{}]", result.getRecordMetadata().partition());
        LOGGER.info("[JobOfferServiceImpl] Topic name=[{}]", result.getRecordMetadata().topic());
        LOGGER.info("[JobOfferServiceImpl] Topic offset=[{}]", result.getRecordMetadata().offset());
    }


}
