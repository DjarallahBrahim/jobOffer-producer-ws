package com.kafka.webservice.producer.repository;

import com.kafka.webservice.producer.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, String> {
}
