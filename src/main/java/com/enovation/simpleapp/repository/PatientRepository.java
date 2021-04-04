package com.enovation.simpleapp.repository;

import com.enovation.simpleapp.repository.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Crud repository to interact with the {@link PatientEntity} table.
 */
public interface PatientRepository extends CrudRepository<PatientEntity, Long> {

    /**
     * Fetches the entry from the table on the basis of external reference
     * @return Returns the patient entity against the external reference
     */
    Optional<PatientEntity> findByExternalReference(UUID externalReference);

}
