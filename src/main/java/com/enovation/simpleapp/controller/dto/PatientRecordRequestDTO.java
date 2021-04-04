package com.enovation.simpleapp.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Request DTO with fields for name and address. Can be extended for more fields in future.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRecordRequestDTO {

    @ApiModelProperty("The name of the patient registered. Size: 2-50 chars")
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @ApiModelProperty("The address of the patient registered. Size: 5-100 chars")
    @NotNull
    @Size(min = 5, max = 100)
    private String address;

}
