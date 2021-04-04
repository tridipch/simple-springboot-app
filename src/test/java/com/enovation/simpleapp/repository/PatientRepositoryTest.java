package com.enovation.simpleapp.repository;

import com.enovation.simpleapp.BaseIntegrationTest;
import com.enovation.simpleapp.repository.entity.PatientEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PatientRepositoryTest extends BaseIntegrationTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void testSanityCheck() {
        PatientEntity saved = patientRepository.save(PatientEntity.builder()
                .name("Michael Jordan")
                .externalReference(UUID.randomUUID())
                .address("London, UK")
                .build());

        assertThat(patientRepository.findById(saved.getId())).isPresent();
        patientRepository.deleteById(saved.getId());
    }



}