package com.example.spending.handler;

import static com.example.spending.common.DateConverter.convertDate;

import com.example.spending.domain.exception.ResourceBadRequestException;
import com.example.spending.domain.exception.ResourceNotFoundException;
import com.example.spending.domain.model.ErrorResponse;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handlerResourceNotFoundException(
      ResourceNotFoundException ex) {
    String datetime = convertDate(new Date());
    ErrorResponse error = new ErrorResponse(datetime, 404, "Resource Not Found", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ResourceBadRequestException.class)
  public ResponseEntity<ErrorResponse> handlerResourceBadRequestException(
      ResourceBadRequestException ex) {
    String datetime = convertDate(new Date());
    ErrorResponse error = new ErrorResponse(datetime, 400, "Bad Request", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handlerException(Exception ex) {
    String datetime = convertDate(new Date());
    ErrorResponse error =
        new ErrorResponse(datetime, 500, "Internal Server Error", ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
