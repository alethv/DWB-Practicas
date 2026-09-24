package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Clase encarga de dar los mensajes con las exceptions para el usuario
 * RestExceptionHandler
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    /**
     * Devuleve una excetion para mostrar al usuario
     * @param exception
     * @param request
     * @return ResponsiveEntity
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(exception.getStatus().value());
        response.setError(exception.getStatus());
        response.setMessage(exception.getMessage());
        response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return new ResponseEntity<>(response, response.getError());
    } 

    /**
     * Devuelve una exception para mostrar al usuario
     * @param exception
     * @param request
     * @return ResponsiveEntity
     */
    @ExceptionHandler(DBAccessException.class)
    protected ResponseEntity<ExceptionResponse> handleDBAccessException(DBAccessException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("Error al acceder a la base de datos");
        response.setPath(((ServletWebRequest)request).getRequest().getRequestURI().toString());
        return new ResponseEntity<>(response, response.getError());
    } 
}