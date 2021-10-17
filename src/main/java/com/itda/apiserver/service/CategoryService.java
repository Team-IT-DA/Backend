package com.itda.apiserver.service;

import com.itda.apiserver.domain.MainCategory;
import com.itda.apiserver.repository.MainCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final MainCategoryRepository categoryRepository;

    public void addCategory(String name) { categoryRepository.save(new MainCategory(name));}

    public List<MainCategory> getCategories() {
        return categoryRepository.findAll();
    }
}
