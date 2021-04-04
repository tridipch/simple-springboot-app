package com.enovation.simpleapp.controller;

import com.enovation.simpleapp.BaseIntegrationTest;
import com.enovation.simpleapp.controller.dto.PatientRecordRequestDTO;
import com.enovation.simpleapp.controller.dto.PatientRecordResponseDTO;
import com.enovation.simpleapp.service.PatientRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "test", password = "test", roles = "USER")
class SimpleAppPatientControllerITest extends BaseIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientRecordService patientRecordService;

    @MockBean
    private MeterRegistry meterRegistry;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                .build();
    }

    @AfterEach
    public void exitTest() {
        verifyNoMoreInteractions(patientRecordService);
    }

    @Test
    void testCreate() throws Exception {

        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        final UUID externalReference = randomUUID();
        when(patientRecordService.createPatient(any(), any())).thenReturn(PatientRecordResponseDTO.builder()
                .id(505L).externalReference(externalReference).name("Michael Jordan").address("Home address")
                .build());
        mockMvc.perform(post("/patient/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PatientRecordRequestDTO.builder()
                        .name("Michael Jordan").address("Home address")
                        .build())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(505)))
                .andExpect(jsonPath("externalReference", is(externalReference.toString())));

        verify(patientRecordService).createPatient(any(), any());

    }

    @Test
    void testGetById() throws Exception {

        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        final UUID externalReference = randomUUID();
        when(patientRecordService.getRecord(Mockito.anyLong())).thenReturn(PatientRecordResponseDTO.builder()
                .id(2L).externalReference(externalReference).name("Michael Jordan").address("Home address")
                .build());
        mockMvc.perform(get("/patient/id/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(2)))
                .andExpect(jsonPath("externalReference", is(externalReference.toString())));

        verify(patientRecordService).getRecord(Mockito.anyLong());
    }

    @Test
    void testGetByExternalReference() throws Exception {

        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        final UUID externalReference = randomUUID();
        when(patientRecordService.getRecord(externalReference)).thenReturn(PatientRecordResponseDTO.builder()
                .id(6L).externalReference(externalReference).name("Michael Jordan").address("Home address")
                .build());
        mockMvc.perform(get("/patient/external-reference/" + externalReference)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(6)))
                .andExpect(jsonPath("externalReference", is(externalReference.toString())));

        verify(patientRecordService).getRecord(externalReference);
    }

    @Test
    void testErrorMessageWhenNoRecordFound() throws Exception {

        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        final UUID externalReference = randomUUID();
        when(patientRecordService.getRecord(externalReference)).thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/patient/external-reference/" + externalReference)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode", is("001")))
                .andExpect(jsonPath("errorMessage", is("Patient record does not exist")));

        verify(patientRecordService).getRecord(externalReference);
    }

    @Test
    void testErrorMessageWhenInternalServerError() throws Exception {

        when(meterRegistry.counter(anyString(), anyString(), anyString())).thenReturn(mock(Counter.class));
        final UUID externalReference = randomUUID();
        when(patientRecordService.getRecord(externalReference)).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/patient/external-reference/" + externalReference)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("errorCode", is("999")))
                .andExpect(jsonPath("errorMessage", is("Internal server occured, contact support desk.")));

        verify(patientRecordService).getRecord(externalReference);
    }

    @Test
    void testCreateWithFieldValidationError() throws Exception {

        mockMvc.perform(post("/patient/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PatientRecordRequestDTO.builder()
                        .name("John Wick").address("A")
                        .build())))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("errorCode", is("002")))
                .andExpect(jsonPath("errorMessage", is("Input field violates constraint. Please correct and try again.")));

    }

}