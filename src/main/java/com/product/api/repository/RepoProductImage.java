package com.product.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.product.api.entity.ProductImage;

@Repository
public interface RepoProductImage extends JpaRepository<ProductImage, Integer> {

    /**
     * Obtiene las imágenes asociadas a un producto filtradas por status.
     * @param productId Identificador del producto.
     * @param status Status numérico de la imagen.
     * @return Lista de imágenes asociadas.
     */
    //@Query(value = "SELECT * FROM product_image WHERE product_id = :product_id AND status = :status", nativeQuery = true)
    List<ProductImage> findByProductIdAndStatus(Integer productId, Integer status);

    /**
     * Obtiene una imagen específica asociada a un producto mediante sus identificadores.
     * @param productImageId Identificador de la imagen.
     * @param productId Identificador del producto.
     * @return Objeto ProductImage encontrado.
     */
    //@Query(value = "SELECT * FROM product_image WHERE product_image_id = :product_image_id AND product_id = :product_id", nativeQuery = true)
    ProductImage findByProductImageIdAndProductId(Integer productImageId, Integer productId);
}