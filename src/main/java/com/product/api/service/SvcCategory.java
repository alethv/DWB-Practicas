package com.product.api.service;

import com.product.api.dto.DtoCategoryIn;
import com.product.api.entity.Category;
import java.util.List;

public interface SvcCategory {
    public List<Category> findAll();
    public List<Category> findActive();
    public List<Category> findChilds(Integer id);
    public void create(DtoCategoryIn dto);
    public void update(DtoCategoryIn dto, Integer id);
    public void enable(Integer id);
    public void disable(Integer id);
}
