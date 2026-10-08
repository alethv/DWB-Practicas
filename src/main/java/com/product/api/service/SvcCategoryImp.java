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

    /** Atributos */
    final RepoCategory repo;

    /** Metodos */

    /**
     * Método constructor de la clase
     * @param repo Repositorio de la entidad Category
     */
    public SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    /**
     * Método que obtiene todas las categorías de la tienda.
     * @return List<Category> Lista con todas las categorías registradas.
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
     * Método que devuelve únicamente las categorías activas (status 1) de la tienda.
     * @return List<Category> Lista con las categorías activas.
     */
    @Override
    public List<Category> findActive() {
        try {
		    return repo.findActive();
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    /**
     * Método que obtiene todas las categorías hijas asociadas a una categoría padre.
     * @param id Identificador de la categoría padre.
     * @return List<Category> Lista de las categorías hijas.
     */
    @Override
    public List<Category> findChilds(Integer id) {
        validateId(id);
        try {
		    return repo.findByParentCategoryId(id);
	    } catch (DataAccessException e) {
        	throw new DBAccessException(e);
        }
    }

    /**
     * Método que registra una nueva categoría en el sistema.
     * @param dto Objeto con los datos de la categoría a crear.
     */
    @Override
    public void create(DtoCategoryIn dto) {
        validateParentCategory(dto.getParentCategoryId(), null);

        try {
            repo.create(dto.getCategory(), dto.getTag(), dto.getParentCategoryId());
        } catch(DataAccessException e) {
            handleDuplicationErrors(e, "crear");
        }
    }
    
    /**
     * Método que actualiza la información de una categoría existente.
     * @param dto Objeto con los nuevos datos de la categoría.
     * @param id Identificador de la categoría a actualizar.
     */
    @Override
    public void update(DtoCategoryIn dto, Integer id) {
        validateId(id);

        try {
            repo.update(dto.getCategory(), dto.getTag(), id);
        } catch(DataAccessException e){
            handleDuplicationErrors(e, "actualizar");
        }
    }

    /**
     * Método que activa una categoría cambiando su status a 1.
     * @param id Identificador de la categoría a activar.
     */
    @Override
    public void enable(Integer id) {
        validateId(id);
        try {
            repo.updateStatus(id, 1);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al activar la categoría");
        }
    }
    
    /**
     * Método que desactiva una categoría cambiando su status a 0.
     * Impide la desactivación si la categoría tiene hijas asociadas.
     * @param id Identificador de la categoría a desactivar.
     */
    @Override
    public void disable(Integer id) {
        validateId(id);

        // Regla: No es posible eliminar una categoría si tiene categorías hijas
        List<Category> childs = repo.findByParentCategoryId(id);
        if (childs != null && !childs.isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "No es posible eliminar una categoría si tiene categorías hijas");
        }

        try {
            repo.updateStatus(id, 0);
        } catch(DataAccessException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Error al desactivar la categoría");
        }
    }


    // --- Métodos de validación auxiliares ---

    /**
     * Método auxiliar que verifica si el ID de una categoría existe en la base de datos.
     * @param id Identificador de la categoría a validar.
     */
    private void validateId(Integer id){
        if (repo.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "El id de la categoría no existe");
        }
    }

    /**
     * Método auxiliar que verifica las reglas de negocio de la categoría padre.
     * Asegura que exista, esté activa y que una categoría no sea padre de sí misma.
     * @param parentCategoryId Identificador de la categoría padre proporcionada.
     * @param currentCategoryId Identificador de la categoría que se está modificando (null en creaciones).
     */
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

    /**
     * Método auxiliar para capturar excepciones de base de datos e identificar si violan 
     * restricciones de unicidad (nombres o etiquetas duplicadas).
     * @param e Excepción arrojada por Spring Data.
     * @param action Acción en formato de texto ("crear" o "actualizar") para el mensaje de error por defecto.
     */
    private void handleDuplicationErrors(DataAccessException e, String action) {
        Throwable root = e.getRootCause();
        String msg = (root != null) ? root.getMessage() : e.getMessage();
        if (msg != null) {
            if (msg.contains("ux_category_category"))
                throw new ApiException(HttpStatus.CONFLICT, "El nombre de la categoría ya está en uso");
            if (msg.contains("ux_category_tag"))
                throw new ApiException(HttpStatus.CONFLICT, "El tag de la categoría ya está en uso");
        }
        throw new ApiException(HttpStatus.BAD_REQUEST, "Error al " + action + " la categoría");
    }
}