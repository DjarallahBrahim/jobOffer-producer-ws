package com.kafka.webservice.producer.controler;

import com.kafka.webservice.producer.exception.ErrorMessage;
import com.kafka.webservice.producer.models.JobOffer;
import com.kafka.webservice.producer.services.JobOfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/job-offer")
public class JobOfferController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JobOfferService jobOfferService;

    @PostMapping("/publish")
    public ResponseEntity<Object> createJobOffer(@RequestBody JobOffer jobOffer) {
        // Do something with the request
        String jobOfferId = null;
        try {
            jobOfferId = jobOfferService.createJobOffer(jobOffer);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(), e.getMessage(), "/publish"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(jobOfferId);
    }
}
