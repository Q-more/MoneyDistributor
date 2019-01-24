package com.mediatoolkit.moneydistributor.api.web;

import com.mediatoolkit.moneydistributor.api.exceptions.TransactionException;
import com.mediatoolkit.moneydistributor.api.exceptions.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({UserException.class, TransactionException.class})
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        LOG.info("Bad request", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> unexpectedExceptionHandler(Exception ex) {
        LOG.error("Internal server error", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOG.info("Validation error");
        String msg = ex.getBindingResult().getAllErrors().stream()
            .map(error -> error.getObjectName() + ":" + error.getDefaultMessage())
            .collect(Collectors.joining("\n"));
        return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
    }
}
