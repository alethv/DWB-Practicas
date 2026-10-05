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

    /** Atributos de la clase */
    @Autowired
    private SvcCategory svc;

    /**
     * Endpoint que obtiene todas las categorías de la tienda.
     * @return Lista de objetos Category con status HTTP 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
	    return ResponseEntity.ok(svc.findAll());
    }

    /**
     * Endpoint que devuelve solo las categorías activas (status 1) de la tienda.
     * @return Lista de objetos Category activos con status HTTP 200 (OK).
     */
    @GetMapping("/active")
     public ResponseEntity<List<Category>> findActive(){
        return ResponseEntity.ok(svc.findActive());
    }

    /**
     * Endpoint que obtiene las categorías hijas asociadas a una categoría padre.
     * @param id Identificador de la categoría padre.
     * @return Lista de objetos Category (hijas) con status HTTP 200 (OK).
     */
    @GetMapping("/{id}/childs")
     public ResponseEntity<List<Category>> findChilds(@PathVariable Integer id){
        return ResponseEntity.ok(svc.findChilds(id));
    }

    /**
     * Endpoint para registrar una nueva categoría en el sistema.
     * @param dto Objeto validado con los datos de la nueva categoría.
     * @return Mensaje de confirmación de registro con status HTTP 200 (OK).
     */
    @PostMapping()
    public ResponseEntity<String> create(@Valid @RequestBody DtoCategoryIn dto) {
        svc.create(dto);
        return ResponseEntity.ok().body("La categoría ha sido registrada");
    }

    /**
     * Endpoint para actualizar los datos de una categoría existente.
     * @param dto Objeto validado con los nuevos datos de la categoría.
     * @param id Identificador de la categoría a actualizar.
     * @return Mensaje de confirmación de actualización con status HTTP 200 (OK).
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@Valid @RequestBody DtoCategoryIn dto, @PathVariable Integer id){
        svc.update(dto, id);
        return ResponseEntity.ok().body("La categoría ha sido actualizada");
    }

    /**
     * Endpoint para activar una categoría (cambia su status a 1).
     * @param id Identificador de la categoría a activar.
     * @return Mensaje de confirmación de activación con status HTTP 200 (OK).
     */
    @PatchMapping("/{id}/enable")
    public ResponseEntity<String> enable(@PathVariable Integer id) {
        svc.enable(id);
        return ResponseEntity.ok().body("La categoría ha sido activada");
    }

    /**
     * Endpoint para desactivar una categoría (cambia su status a 0).
     * @param id Identificador de la categoría a desactivar.
     * @return Mensaje de confirmación de desactivación con status HTTP 200 (OK).
     */
    @PatchMapping("/{id}/disable")
    public ResponseEntity<String> disable(@PathVariable Integer id) {
        svc.disable(id);
        return ResponseEntity.ok().body("La categoría ha sido desactivada");
    }
}