package com.product.api.repository;

import com.product.api.entity.Category;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepoCategory extends JpaRepository<Category, Integer> {

    /**
     * Obtiene todas las categorías registradas en la base de datos.
     * @return Lista de categorías ordenadas alfabéticamente por su nombre.
     */
    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> findAll();

    /**
     * Obtiene únicamente las categorías activas en el sistema.
     * @return Lista de categorías con status 1, ordenadas alfabéticamente por su nombre.
     */
    @Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
    List<Category> findActive();

    /**
     * Consulta derivada (Query Method) para buscar categorías por su status.
     * @param status Status numérico a buscar (ej. 1 para activo, 0 para inactivo).
     * @return Lista de categorías filtradas por status y ordenadas por nombre.
     */
    List<Category> findByStatusOrderByCategory(@Param("status") Integer status);

    /**
     * Obtiene todas las categorías hijas asociadas a una categoría padre.
     * @param parent_category_id Identificador de la categoría padre.
     * @return Lista de categorías hijas ordenadas por nombre.
     */
    @Query(value="SELECT * FROM category WHERE parent_category_id = :parent_category_id ORDER BY category", nativeQuery = true)
    List<Category> findByParentCategoryId(@Param("parent_category_id")Integer parent_category_id);

    /**
     * Inserta un nuevo registro de categoría en la base de datos.
     * El status se inicializa automáticamente en 1 (activo).
     * @param category Nombre de la nueva categoría.
     * @param tag Tag de la categoría.
     * @param parentCategoryId Identificador de la categoría padre (puede ser nulo).
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value="INSERT INTO category(category, tag, status, parent_category_id) VALUES(:category, :tag, 1, :parent_category_id)", nativeQuery = true)
    public void create(@Param("category") String category, @Param("tag") String tag,  @Param("parent_category_id") Integer parentCategoryId);

    /**
     * Actualiza los datos principales de una categoría existente.
     * @param category Nuevo nombre de la categoría.
     * @param tag Nuevo tag de la categoría.
     * @param parent_category_Id Nuevo identificador de la categoría padre.
     * @param category_id Identificador de la categoría a actualizar.
     */
    @Modifying(clearAutomatically =true, flushAutomatically = true)
    @Transactional
    @Query(value="UPDATE category SET category = :category, tag = :tag WHERE category_id = :category_id", nativeQuery=true)
    public void update(@Param("category") String category, @Param("tag") String tag, @Param ("category_id") Integer category_id);
    
    /**
     * Actualiza únicamente el status de una categoría (para activar o desactivar).
     * @param category_id Identificador de la categoría a modificar.
     * @param status Nuevo valor de status (1 para activar, 0 para desactivar).
     */
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value="UPDATE category SET status = :status WHERE category_id = :category_id", nativeQuery = true)
    public void updateStatus(@Param("category_id") Integer category_id, @Param("status") Integer status);
}