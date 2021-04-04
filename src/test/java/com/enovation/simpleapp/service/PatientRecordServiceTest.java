package com.enovation.simpleapp.service;

import com.enovation.simpleapp.controller.dto.PatientRecordRequestDTO;
import com.enovation.simpleapp.controller.dto.PatientRecordResponseDTO;
import com.enovation.simpleapp.mapper.PatientRecordDTOMapper;
import com.enovation.simpleapp.repository.PatientRepository;
import com.enovation.simpleapp.repository.entity.PatientEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.enovation.simpleapp.mapper.PatientRecordDTOMapper.MAPPER_INSTANCE;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientRecordServiceTest {

    @InjectMocks
    private PatientRecordService patientRecordService;

    @Mock
    private PatientRepository patientRepository;

    @Test
    void createPatient() {
        final PatientRecordRequestDTO patientRecordRequestDTO = PatientRecordRequestDTO.builder()
                .name("John Wick").address("Amsterdam")
                .build();

        final UUID extRef = randomUUID();
        when(patientRepository.save(MAPPER_INSTANCE.toEntity(patientRecordRequestDTO, extRef)))
                .thenReturn(PatientEntity.builder()
                        .address("Amsterdam").name("John Wick").externalReference(extRef).id(1L)
                        .build());
        final PatientRecordResponseDTO patientRecordResponseDTO = patientRecordService.createPatient(patientRecordRequestDTO, extRef);
        assertEquals("John Wick", patientRecordResponseDTO.getName());

        verify(patientRepository).save(any());
        verifyNoMoreInteractions(patientRepository);
    }

    @Test
    void getRecordById() {
        final UUID extRef = randomUUID();
        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(PatientEntity.builder()
                        .address("Amsterdam").name("John Wick").externalReference(extRef).id(1L)
                        .build()));
        final PatientRecordResponseDTO patientRecordResponseDTO = patientRecordService.getRecord(1L);
        assertEquals("John Wick", patientRecordResponseDTO.getName());

        verify(patientRepository).findById(1L);
        verifyNoMoreInteractions(patientRepository);

    }

    @Test
    void getRecordByReference() {
        final UUID extRef = randomUUID();
        when(patientRepository.findByExternalReference(extRef))
                .thenReturn(Optional.of(PatientEntity.builder()
                        .address("Amsterdam").name("John Wick").externalReference(extRef).id(1L)
                        .build()));
        final PatientRecordResponseDTO patientRecordResponseDTO = patientRecordService.getRecord(extRef);
        assertEquals("John Wick", patientRecordResponseDTO.getName());

        verify(patientRepository).findByExternalReference(extRef);
        verifyNoMoreInteractions(patientRepository);
    }
}