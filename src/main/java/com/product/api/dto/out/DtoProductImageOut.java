package com.product.api.dto.out;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DtoProductImageOut {

    @JsonProperty("product_image_id")
    private Integer productImageId;

    @JsonProperty("product_id")
    private Integer product_id;

    @JsonProperty("image")
    private String image;

    public DtoProductImageOut(Integer productImageId, Integer product_id, String image) {
		super();
		this.productImageId = productImageId;
		this.product_id = product_id;
        this.image = image;
	}

    public Integer getProductImageId() {
        return  productImageId;
    }

    public void setProductImageId(Integer productImageId) {
        this.productImageId = productImageId;
    }

    public Integer getProductId() {
        return  product_id;
    }

    public void setProductId(Integer product_id) {
        this.product_id = product_id;
    }

    public String getImage() {
        return  image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
