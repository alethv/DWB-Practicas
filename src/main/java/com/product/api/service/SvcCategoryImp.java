package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {

    final RepoCategory repo;

    public SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> findAll() {
        try {
		    return repo.findAll();
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findActive() {
        return repo.findActive();
    }

    @Override
    public List<Category> findChilds(Integer id) {
        return repo.findByParentCategoryId(id);
    }
    
    @Override
    public void create(DtoCategoryIn dto) {
        try {
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch(DataAccessException e) {
            String msg = e.getLocalizedMessage();
            if (msg != null && msg.contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
            if (msg != null && msg.contains("ux_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría");
        }
    }
    
    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        try{
            repo.update(dto.getCategory(), dto.getTag(), dto.getParentCategoryId(), id);
        }catch(DataAccessException e){
            String msg = e.getLocalizedMessage();
            if(msg!=null && msg.contains("ux_category"))
                throw new ApiException(
            HttpStatus.CONFLICT,
            "El nombre de la region ya esta registrado"
        );
        }
    }
    
    @Override
    public void enable(Integer id) {
        try {
            repo.updateStatus(id, 1);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }
    
    @Override
    public void disable(Integer id) {
        try {
            repo.updateStatus(id, 0);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");
        }
    }
}