package com.product.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

    /**
     * Atributos de la clase
     */

    // Integer con valor del identificador de la categoria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    // String con valor de el nombre de la categoria 
    @Column(name = "category")
    private String category;

    // String con valor de nombre de la categia abreviado usado como etiqueta
    @Column(name = "tag")
    private String tag;

    // Integer con valor de el identificador de la cateria padre de la categoria con posibilidad de ser nulo si no tiene padre
    @Column(name = "parent_category_id")
    private Integer parentCategoryId;

    //Integer con valor 1 o 0 si la categoria esta activa siendo 1 o 0 si esta categoria es borrada
    @Column(name = "status")
    private Integer status;

    /**
     * Metodo constructor de la clase 
     */
    public Category() {
    }
    
    /**
     * Metodo Constructor de la clase con Parametros para los atributos de la categoria a crear
     * @param String category para el nombre de la categoria
     * @param String para la etiqueta o tag de la categoria, de preferencia en mayusculas y el nombre de la categoria pero abreviado 
     * @param Integer con valor a un Id ya existente para marcar que esta categoria es hija de dicha categoria,
     * no puede ser el mismo Id de creacion de la categoria y puede ser null si no es hija de alguna categoria
     */
    public Category(String category, String tag, Integer parentCategoryId) {
        this.category = category;
        this.tag = tag;
        this.parentCategoryId = parentCategoryId;
        this.status = 1;
    }

    /**
     * Metodos getter y setters 
     */
    
    /**
     * @return Integer con valor al id de la categoria
     */
    public Integer getCategoryId() { return categoryId; }

    /**
     * @param Integer para cambiar el id de una categoria
     */
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    /**
     * @return String con el nombre de la categoria 
     */
    public String getCategory() { return category; }

    /**
     * @param String para cambiar el nombre de una categoria 
     */
    public void setCategory(String category) { this.category = category; }

    /**
     * @return String con valor del tag de la categoria
     */
    public String getTag() { return tag; }

    /**
     * @param String con valor al nuevo tag para la categoria
     */
    public void setTag(String tag) { this.tag = tag; }

    /**
     * @return Integer con valor a un numero si es hija de una categoria o null si no es hija de alguna categoria
     */
    public Integer getParentCategoryId() { return parentCategoryId; }

    /**
     * @param Integer con valor a un Id de una categoria ya existente para ser hija de esta categoria o null en caso de 
     * ya no ser hija de alguna categoria
     */
    public void setParentCategoryId(Integer parentCategoryId) { this.parentCategoryId = parentCategoryId; }

    /**
     * @return Integer con valor a 1 si la categoria esta activa o 0 si esta categoria no esta activa
     */
    public Integer getStatus() { return status; }

    /**
     * @param Integer con valor 1 si se va activar la categoria o 0 si se descativara la categoria 
     */
    public void setStatus(Integer status) { this.status = status; }

    /**
     * Metodo que da un String con todos los atributos de la categoria en un formato con llaves y separados con comas
     * {Atributo1, Atributo2,...,Atributo5}
     * @return String con los atributos en un formato para poderse imprimir
     */
    @Override
    public String toString() {
        return "{" + categoryId + ", \"" + category + "\", \"" + tag + "\", " 
                + parentCategoryId + ", " + status + "}";
    }
}