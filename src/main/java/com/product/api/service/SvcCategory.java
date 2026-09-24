package com.product.api.service;

import com.product.api.entity.Category;
import java.util.List;

import org.springframework.http.ResponseEntity;

/**Interface */
public interface SvcCategory {
    /**Metodos de la inteface */
    public ResponseEntity<List<Category>> getCategories();
    public ResponseEntity<List<Category>> getActiveCategories();
}