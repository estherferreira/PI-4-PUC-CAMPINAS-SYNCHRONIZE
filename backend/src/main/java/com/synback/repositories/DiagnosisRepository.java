package com.synback.repositories;

import com.synback.models.Diagnosis;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DiagnosisRepository extends MongoRepository<Diagnosis, String> {
}
