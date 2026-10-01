package com.product.api.dto;

//import org.antlr.v4.runtime.misc.NotNull;

// se agrego esta porque la de arriba que vimos en lab daba error ya que esta nueva es para
// springboot 3.x o mas
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoCategoryIn {
    @JsonProperty("category")
    @NotNull(message="La categoría es obligatoria")
    private String category;

    @JsonProperty("tag")
    @NotNull(message="La etiqueta es obligatoria")
    private String tag;
    
    @JsonProperty("parentCategoryId")
    private Integer parentCategoryId;

    public String getCategory() {
        return  this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

      public String getTag() {
        return  this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

      public Integer getParentCategoryId() {
        return  this.parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
