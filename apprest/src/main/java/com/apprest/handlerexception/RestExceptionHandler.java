package com.apprest.handlerexception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handlerResourceNotFoundException(EntityNotFoundException ex){
        ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException
    (MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors =  e.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

        ValidationErrorDetails validationErrorDetails = new ValidationErrorDetails();
        validationErrorDetails.setStatus (HttpStatus.BAD_REQUEST.value());
        validationErrorDetails.setTitle("Operação Inválida");
        validationErrorDetails.setMessage("Requisição Inválida");
        validationErrorDetails.setField(fields);
        validationErrorDetails.setFieldMessage(fieldMessages);

        return new ResponseEntity<>(validationErrorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleException(HttpMessageNotReadableException exception, HttpServletRequest request) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage("Arguments Invalid ");
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setTitle("Request Invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {
        ErrorMessage err = new ErrorMessage();
        err.setMessage("Arguments Invalid ");
        err.setStatus(HttpStatus.BAD_REQUEST.value());
        err.setTitle("Request Invalid");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> dataBase(DataIntegrityViolationException exception, HttpServletRequest request) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage err = new ErrorMessage();
        err.setMessage("Database Exception");
        err.setStatus(status.value());
        err.setTitle("Request Invalid");

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorMessage> typeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage err = new ErrorMessage();
        err.setMessage("Type Mismatch Exception");
        err.setStatus(status.value());
        err.setTitle("Request Invalid");

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> illegalArgument(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMessage err = new ErrorMessage();
        err.setMessage("Illegal Argument");
        err.setStatus(status.value());
        err.setTitle("Request Invalid");

        return ResponseEntity.status(status).body(err);
    }

}