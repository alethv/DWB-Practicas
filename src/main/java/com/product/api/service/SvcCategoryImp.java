package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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

    /**
     *Metodo que obtiene todas las categoria de la tienda
     * @return List Category
     * 
     */
    @Override
    public List<Category> findAll() {
        try {
		    return repo.findAll();
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    /**    
     * Metodo que devuelve todas las clases activas de la tienda
     * @return List Category
     */
    @Override
    public List<Category> findActive() {
        try {
		    return repo.findActive();
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    @Override
    public List<Category> findChilds(Integer id) {
        try {
            validateId(id);
		    return repo.findByParentCategoryId(id);
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    @Override
    public void create(DtoCategoryIn dto) {
        try {
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch(DataAccessException e) {
            Throwable root = e.getRootCause();
            String msg = (root != null) ? root.getMessage() : e.getMessage();
            if (msg != null) {
                if (msg.contains("ux_category_category"))
                    throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
                if (msg.contains("ux_category_tag"))
                    throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
                if (msg.contains("fk_parent_category"))
                    throw new ApiException(HttpStatus.NOT_FOUND, "La categoría padre no existe");
                if (msg.contains("La categoría padre no existe o está inactiva"))
                    throw new ApiException(HttpStatus.NOT_FOUND, "La categoría padre no existe o está inactiva");
                if (msg.contains("Una categoría no pude ser padre de si misma"))
                    throw new ApiException(HttpStatus.BAD_REQUEST, "La categoría no puede ser su propio padre");
            }
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al crear la categoría");
        }
    }
    
    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        try {
            repo.update(dto.getCategory(), dto.getTag(), dto.getParentCategoryId(), id);
        } catch(DataAccessException e){
            String msg = e.getLocalizedMessage();
            if (msg!=null && msg.contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está registrado");
        }
    }

    @Override
    public void enable(Integer id) {
        try {
            validateId(id);
            repo.updateStatus(id, 1);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }
    
    @Override
    public void disable(Integer id) {
        try {
            validateId(id);
            repo.updateStatus(id, 0);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");
        }
    }

    private void validateId(Integer id){
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    }

    private void validateParentCategory(Integer parentCategoryId, Integer currentCategoryId) {
        if (parentCategoryId != null) {
            // Regla: Una categoría no puede ser padre de sí misma
            if (currentCategoryId != null && parentCategoryId.equals(currentCategoryId)) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Una categoría no puede ser padre de sí misma");
            }

            // Regla: Si tiene padre, debe existir y tener estatus 1 (activo)
            Category parent = repo.findById(parentCategoryId).orElse(null);
            if (parent == null || parent.getStatus() == 0) {
                throw new ApiException(HttpStatus.NOT_FOUND, "La categoría padre no existe o está inactiva");
            }
        }
    }

    private void handleDuplicationErrors(DataAccessException e, String action) {
        Throwable root = e.getRootCause();
        String msg = (root != null) ? root.getMessage() : e.getMessage();
        if (msg != null) {
            if (msg.contains("ux_category_category") || msg.contains("ux_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
            if (msg.contains("ux_category_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "Error al " + action + " la categoría");
    }
}