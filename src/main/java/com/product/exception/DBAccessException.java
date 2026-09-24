package com.product.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends RuntimeException{

    /*Atributos de la clase */
    private static final long serialVersionUID= 1L;
    private DataAccessException exception;
    
    /*Metodos de la clase */

    /**
     * Metodo que da el tipo de exception
     * @param e, exception 
     */
    public DBAccessException(DataAccessException e){
        this.exception = e;
    }
}