package com.enovation.simpleapp.controller.dto;

import lombok.Builder;
import lombok.Data;

/**
 * On the event of any error encountered during the processing of the request, the response
 * will render an errorMessage and an errorCode in the json
 */
@Data
@Builder
public class ErrorDTO {
    private String errorCode;
    private String errorMessage;
}
