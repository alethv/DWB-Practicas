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

    @Query(value = "SELECT * FROM category ORDER BY category", nativeQuery = true)
    List<Category> findAll();

    @Query(value = "SELECT * FROM category WHERE status = 1 ORDER BY category", nativeQuery = true)
    List<Category> findActive();

    List<Category> findByStatusOrderByCategory(@Param("status") Integer status);

    @Query(value="SELECT * FROM category WHERE parent_category_id = :parent_category_id ORDER BY category", nativeQuery = true)
    List<Category> findByParentCategoryId(@Param("parent_category_id")Integer parent_category_id);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value="INSERT INTO category(category, tag, status, parent_category_id) VALUES(:category, :tag, 1, :parent_category_id)", nativeQuery = true)
    public void create(@Param("category") String category, @Param("tag") String tag,  @Param("parent_category_id") Integer parentCategoryId);

    @Modifying(clearAutomatically =true, flushAutomatically = true)
    @Transactional
    @Query(value="UPDATE category SET category = :category, tag = :tag, parent_category_id = :parent_category_id  WHERE category_id = :category_id", nativeQuery=true)
    public void update(@Param("category") String category, @Param("tag") String tag, @Param("parent_category_id") Integer parent_category_Id, @Param ("category_id") Integer category_id);
    
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    @Query(value="UPDATE category SET status = :status WHERE category_id = :category_id", nativeQuery = true)
    public void updateStatus(@Param("category_id") Integer category_id, @Param("status") Integer status);
}