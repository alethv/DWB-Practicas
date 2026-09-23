package com.product.api.controller;

import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    private SvcCategory svc;

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        return svc.getCategories();
    }

    @GetMapping("/active")
    public ResponseEntity<List<Category>> getActiveCategories() {
        return svc.getActiveCategories();
    }
}