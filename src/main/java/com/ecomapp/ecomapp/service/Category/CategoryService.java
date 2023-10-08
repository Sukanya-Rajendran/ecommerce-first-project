package com.ecomapp.ecomapp.service.Category;

import com.ecomapp.ecomapp.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Category save(Category category);

    Category getByName(String name);

//   Category getByType(String name);

    Category updateCategory(Category category);

    void addCategory(Category category);

    Optional<Category> getCategoryByid(int id);

    public void toggleTheCategory(long id);


    List<Category> getCategoriesByName(String name);
}


