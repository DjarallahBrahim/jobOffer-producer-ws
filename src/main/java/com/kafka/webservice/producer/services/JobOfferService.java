package com.kafka.webservice.producer.services;

import com.kafka.webservice.producer.models.JobOffer;

public interface JobOfferService {

    String createJobOffer(JobOffer jobOffer) throws Exception;
}
