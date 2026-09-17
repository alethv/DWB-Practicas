package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ExceptionResponse{

    @jsonformat(shape = JsonFormat.Shape.String, pattern= "yyyy.MM.dd hh.mm.ss")
    private LocalDateTime timestamp;
    private Integer status;
    private httpStatus error;
    private String message;
    private String path;

    public ExceptionResponse(){
        super();
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public Integer getStatus (){
        return status;
    }

    public httpStatus getHttpStatus(){
        return error;
    }

    public String getMessage(){
        return message;
    }

    public String getPath(){
        return path;
    }

    public void setTimestamp(LocalDateTime timestamp){
        this.timestamp = timestamp;
    }
    public void setStatus(Integer status){
        this.status= status;
    }

    public void setHttpStatus(httpStatus error){
        this.error = error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setPath (String path){
        this.path = path;
    }

}
