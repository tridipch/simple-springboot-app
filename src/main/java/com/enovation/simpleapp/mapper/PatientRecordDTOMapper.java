package com.enovation.simpleapp.mapper;

import com.enovation.simpleapp.controller.dto.PatientRecordRequestDTO;
import com.enovation.simpleapp.controller.dto.PatientRecordResponseDTO;
import com.enovation.simpleapp.repository.entity.PatientEntity;

import java.util.UUID;

public enum PatientRecordDTOMapper {
    MAPPER_INSTANCE;

    public PatientRecordResponseDTO toResponseDTO(PatientEntity entity) {
        return PatientRecordResponseDTO.builder()
                .externalReference(entity.getExternalReference())
                .name(entity.getName())
                .address(entity.getAddress())
                .id(entity.getId())
                .build();
    }

    public PatientEntity toEntity(PatientRecordRequestDTO request, UUID extRef) {
        return PatientEntity.builder()
                .address(request.getAddress())
                .externalReference(extRef)
                .name(request.getName())
                .build();
    }
}
