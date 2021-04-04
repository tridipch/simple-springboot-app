package com.enovation.simpleapp.service;

import com.enovation.simpleapp.controller.dto.PatientRecordRequestDTO;
import com.enovation.simpleapp.controller.dto.PatientRecordResponseDTO;
import com.enovation.simpleapp.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static com.enovation.simpleapp.mapper.PatientRecordDTOMapper.MAPPER_INSTANCE;
import static java.util.UUID.randomUUID;

/**
 * Service layer to process the requests from the controller and a bridge between the repository layer.
 */
@Service
@AllArgsConstructor
public class PatientRecordService {
    private final PatientRepository patientRepository;

    /**
     * Creates the patient entry in the database.
     * @param request
     * @return the patient response DTO
     */
    public PatientRecordResponseDTO createPatient(final PatientRecordRequestDTO request, final UUID externalRef) {
        return MAPPER_INSTANCE.toResponseDTO(patientRepository
                .save(MAPPER_INSTANCE.toEntity(request, externalRef)));
    }

    /**
     * Returns the patient entry from the database based on the id.
     * @param id - primary key of the table
     * @return the patient response DTO
     */
    public PatientRecordResponseDTO getRecord(final Long id) {
        return MAPPER_INSTANCE.toResponseDTO(patientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Returns the patient entry from the database based on the externalReference.
     * @param externalReference
     * @return the patient response DTO
     */
    public PatientRecordResponseDTO getRecord(final UUID externalReference) {
        return MAPPER_INSTANCE.toResponseDTO(patientRepository.findByExternalReference(externalReference)
                .orElseThrow(EntityNotFoundException::new));
    }
}
