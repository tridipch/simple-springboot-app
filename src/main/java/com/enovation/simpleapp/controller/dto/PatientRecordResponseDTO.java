package com.enovation.simpleapp.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The response DTO that will contain information about the entry created or fetched.
 */
@Data
@Builder
public class PatientRecordResponseDTO {

    @ApiModelProperty("The id of patient record created. To be used for internal purpose.")
    private Long id;

    @ApiModelProperty("The external reference of the record created, can be shared externally")
    private UUID externalReference;

    private String name;

    private String address;


}
