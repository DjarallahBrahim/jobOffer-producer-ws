package com.kafka.webservice.producer.services;


import com.kafka.webservice.producer.entity.JobOffer;

import java.util.concurrent.ExecutionException;

public interface JobOfferService {

    public JobOffer saveJobOfferToBDD(JobOffer jobOffer);
    String savJobOfferToBddKafka(JobOffer jobOffer) throws Exception;

    void sendEventToKafka(JobOffer jobOffer) throws InterruptedException, ExecutionException;

}
