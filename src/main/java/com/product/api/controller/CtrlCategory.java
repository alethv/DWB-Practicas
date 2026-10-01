package com.product.api.controller;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    @Autowired
    private SvcCategory svc;

    @GetMapping
    public List<Category> getCategories() {
        return svc.findAll();
    }

    @GetMapping("/active")
    public List<Category> getActiveCategories() {
        return svc.findActive();
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody DtoCategoryIn dto) {
        svc.create(dto);
        return ResponseEntity.ok().body("La categoría ha sido registrada");
    }

    @PatchMapping("(id)/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        svc.enable(id);
        return ResponseEntity.ok().body("Categoría activada");
    }

    @PatchMapping("(id)/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        svc.disable(id);
        return ResponseEntity.ok().body("Categoría desactivada");
    }

}