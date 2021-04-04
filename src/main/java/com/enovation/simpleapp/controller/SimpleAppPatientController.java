package com.enovation.simpleapp.controller;

import com.enovation.simpleapp.controller.dto.PatientRecordRequestDTO;
import com.enovation.simpleapp.controller.dto.PatientRecordResponseDTO;
import com.enovation.simpleapp.service.PatientRecordService;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Front controller to take the requests for creating patient record or fetch them
 */
@RestController
@RequestMapping("/patient")
@AllArgsConstructor
public class SimpleAppPatientController {
    private final PatientRecordService patientRecordService;
    private final MeterRegistry meterRegistry;

    private static final String API_COUNTER_NAME = "api-counter";
    private static final String CREATE_API = "create";
    private static final String GET_BY_ID_API = "get-by-id";
    private static final String GET_BY_EXT_REF_API = "get-by-ext";

    /**
     * Creates a new patient record in the DB table
     * @param request
     * @return PatientRecordResponseDTO
     */
    @ApiOperation(value = "Creates a new patient record")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Patient record successfully created")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public PatientRecordResponseDTO createPatient(@NotNull @Valid @RequestBody PatientRecordRequestDTO request) {
        registerCounter(API_COUNTER_NAME, CREATE_API);
        return patientRecordService.createPatient(request, randomUUID());
    }

    /**
     * Fetches any record if exists for the input id
     * @param id
     * @return PatientRecordResponseDTO
     */
    @ApiOperation(value = "Get a patient record based on id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Patient record successfully fetched")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/id/{id}")
    public PatientRecordResponseDTO getPatientRecordById(@NotNull @PathVariable Long id ) {
        registerCounter(API_COUNTER_NAME, GET_BY_ID_API);
        return patientRecordService.getRecord(id);
    }

    /**
     * Fetches any record if exists for the input externalReference
     * @param externalReference
     * @return PatientRecordResponseDTO
     */
    @ApiOperation(value = "Get a patient record based on id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Patient record successfully fetched")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/external-reference/{externalReference}")
    public PatientRecordResponseDTO getPatientRecordByExternalReference(@NotNull @PathVariable UUID externalReference) {
        registerCounter(API_COUNTER_NAME, GET_BY_EXT_REF_API);
        return patientRecordService.getRecord(externalReference);
    }


    private void registerCounter(final String counterName, final String api) {
        meterRegistry.counter(counterName, "operation", api).increment();
    }

}
