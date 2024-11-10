package com.filmupia.backend.exception;

import com.filmupia.backend.model.ErrorResponseModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FilmupiaApiException.class)
    public ResponseEntity<ErrorResponseModel> handleFilmupiaApiException(
            FilmupiaApiException ex,
            WebRequest request) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseModel> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest request
    ) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseModel, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseModel> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        Set<String> violations = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());

        String errorMessage = String.join(", ", violations);

        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                errorMessage,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponseModel> handleRuntimeException(RuntimeException ex) {
        ErrorResponseModel errorResponseModel = new ErrorResponseModel(
                ex.getLocalizedMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
