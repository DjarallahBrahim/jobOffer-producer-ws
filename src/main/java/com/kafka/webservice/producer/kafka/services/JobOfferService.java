package com.kafka.webservice.kafka_ws.kafka.services;

import com.kafka.webservice.kafka_ws.kafka.models.JobOffer;

public interface JobOfferService {

    String createJobOffer(JobOffer jobOffer) throws Exception;
}
