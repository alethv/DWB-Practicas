package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlProduct {

    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = new ArrayList<>();

        // Categoría 1: Ropa
        Category cat1 = new Category("Ropa", "RP", null);
        cat1.setCategoryId(1);

        // Categoría 2: Calzado
        Category cat2 = new Category("Calzado", "CLZD", 1);
        cat2.setCategoryId(2);

        categories.add(cat1);
        categories.add(cat2);

        return categories;
    }
}