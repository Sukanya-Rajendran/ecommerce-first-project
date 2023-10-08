package com.ecomapp.ecomapp.service.Category;

import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.repository.categoryrepo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category getByName(String name) {
        return categoryRepo.getByName(name);
    }

//    @Override
//    public Category getByType(String name) {
//        return categoryRepo.getByName(name);
//    }

    @Override
    public Category updateCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public void addCategory(Category category) {
         categoryRepo.save(category);
    }

    @Override
    public Optional<Category> getCategoryByid(int id) {
        return Optional.empty();
    }

    @Override
    public void toggleTheCategory(long id) {
        Optional<Category> optionalCategory = categoryRepo.findById(id);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setAvailable(!category.getIsAvailable());
            System.out.println(category);
            categoryRepo.save(category);
        }
    }

    @Override
    public List<Category> getCategoriesByName(String name) {

        return categoryRepo.findByName(name);
    }
}






