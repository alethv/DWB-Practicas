package com.product.api.service;

import com.product.api.entity.Category;
import com.product.api.repository.RepoCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SvcCategoryImp implements SvcCategory {

    final RepoCategory repo;

    public SvcCategoryImp(RepoCategory repo) {
        this.repo = repo;
    }

    @Override
    public List<Category> getCategories() {
        return repo.getCategories();
    }

    @Override
    public List<Category> getActiveCategories() {
        return repo.findByStatusOrderByCategory(1);
    }
}