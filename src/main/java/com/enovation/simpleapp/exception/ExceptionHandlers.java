package com.enovation.simpleapp.exception;

import com.enovation.simpleapp.controller.dto.ErrorDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

/**
 * Exception handler to intercept the exceptions and return more meaningful responses to the front end.
 */
@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ExceptionHandlers {

    /**
     * Wraps the DB entry not found exception thrown within the application.
     * @param ex
     * @return ErrorDTO
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handle(final EntityNotFoundException ex) {
        log.error("Record does not exists", ex);
        return constructErrorDTO(ErrorConstants.PATIENT_RECORD_DOES_NOT_EXIST);
    }

    /**
     * Wraps any other unhandled exception thrown within the application.
     * @param ex
     * @return a readable string.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorDTO handle(final MethodArgumentNotValidException ex) {
        log.error("Input field violates constraint", ex);
        return constructErrorDTO(ErrorConstants.VALIDATION_FAILED);
    }

    /**
     * Wraps any other unhandled exception thrown within the application.
     * @param ex
     * @return a readable string.
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorDTO handle(final Exception ex) {
        log.error("Something went wrong in the application", ex);
        return constructErrorDTO(ErrorConstants.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO constructErrorDTO(ErrorConstants error) {
        return ErrorDTO.builder()
                .errorCode(error.getCode())
                .errorMessage(error.getMessage())
                .build();
    }

}

@RequiredArgsConstructor
@Getter
enum ErrorConstants {
    PATIENT_RECORD_DOES_NOT_EXIST("001", "Patient record does not exist"),
    VALIDATION_FAILED("002", "Input field violates constraint. Please correct and try again."),
    INTERNAL_SERVER_ERROR("999", "Internal server occured, contact support desk.");

    private final String code;
    private final String message;
}