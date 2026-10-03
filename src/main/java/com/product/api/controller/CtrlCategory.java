package com.product.api.controller;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.service.SvcCategory;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CtrlCategory {

    /**Atributos de la clase */
    @Autowired
    private SvcCategory svc;

    /**
     * endpoint que da todas las categorias de la tienda
     * @return List Category
     */
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
	    return ResponseEntity.ok(svc.findAll());
    }

    /**
     * endpoint que devuelve solo las categorias activas de la tienda
     * @return List Category
     */
    @GetMapping("/active")
     public ResponseEntity<List<Category>> findActive(){
        return ResponseEntity.ok(svc.findActive());
    }

    @GetMapping("/{id}/childs")
     public ResponseEntity<List<Category>> findChilds(@PathVariable Integer id){
        return ResponseEntity.ok(svc.findChilds(id));
    }

    @PostMapping()
    public ResponseEntity<String> create(@RequestBody DtoCategoryIn dto) {
        svc.create(dto);
        return ResponseEntity.ok().body("La categoría ha sido registrada");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DtoCategoryIn dto, @PathVariable Integer id){
        svc.update(dto, id);
        return ResponseEntity.ok().body("La categoria ha sido actualizada");
    }

    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        svc.enable(id);
        return ResponseEntity.ok().body("La categoría ha sido activada");
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        svc.disable(id);
        return ResponseEntity.ok().body("La categoría ha sido desactivada");
    }
}