package com.practiseapp.userservice.common.exception;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger loggerException = Logger.getLogger(CustomExceptionHandler.class.getName());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(
                getErrorDetailsWithSevere(ex, request).toString(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({GlobalException.class})
    public ResponseEntity<String> handleGlobalException(GlobalException ex, WebRequest request) {
        return new ResponseEntity<>(
                getErrorDetailsWithSevere(ex, request).toString(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({ParameterNotFoundException.class})
    public ResponseEntity<String> handleException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                getErrorDetailsWithSevere(ex, request).toString(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(
            MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(
                getErrorDetailsWithSevere(ex, request).toString(),
                HttpStatus.BAD_REQUEST
        );
    }



    private ErrorDetails getErrorDetailsWithSevere(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        loggerException.log(Level.SEVERE, errorDetails.toString());
        return errorDetails;
    }
}
