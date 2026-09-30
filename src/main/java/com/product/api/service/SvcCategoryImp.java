package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.DBAccessException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {

    /**Atributos */
    final RepoCategory repo;

    /**Metodos */

    /**
     * Metodo constructor de la clase
     * @param repo
     */
    public SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    /**    (non-Javadoc)
     *Metodo que obtiene todas las categoria de la tienda
     * @return List Category
     * 
     * */

    @Override
    public ResponseEntity<List<Category>> getCategories() {
        try {
            return new ResponseEntity<List<Category>>(repo.getCategories(), HttpStatus.OK);
        } catch(DataAccessException e) {
            throw new DBAccessException(e);
        }
    }

    /**    
     * Metodo que devuelve todas las clases activas de la tienda
     * @return List Category
     */
    @Override
    public ResponseEntity<List<Category>> getActiveCategories() {
        try {
            return new ResponseEntity<List<Category>>(repo.findByStatusOrderByCategory(1), HttpStatus.OK);
        } catch(DataAccessException e) {
            throw new DBAccessException(e);
        }
    }
}