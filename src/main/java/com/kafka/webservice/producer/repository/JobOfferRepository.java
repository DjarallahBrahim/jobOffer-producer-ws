package com.kafka.webservice.producer.repository;

import com.joboffer.ws.core.jpa.entities.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, String> {
}
