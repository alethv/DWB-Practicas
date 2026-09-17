package com.product.api.service;

import java.util.List;

import org.springframework.https.ResponseEntity;
import com.product.api.entity.Category;

public interface SvcCategory{
    public ReponseEntity<List<Categories>> getCategories();
    public ResponseEntity<List<Categories>> getActiveCategories();

}