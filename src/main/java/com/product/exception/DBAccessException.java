package com.product.exception;

public class DBAccessException extends RuntimeException{

    private static final long serialVersionUID= 1L;
    
    private DBAccessException exception;
    
    public DBAccessException(DataAccessException e){
        this.exception = e;
    }

}