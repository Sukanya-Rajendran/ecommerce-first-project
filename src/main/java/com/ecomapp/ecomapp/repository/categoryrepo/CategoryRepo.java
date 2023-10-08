package com.ecomapp.ecomapp.repository.categoryrepo;

import com.ecomapp.ecomapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

     List<Category> findCategoryByIsAvailableTrue();

     @Query("SELECT u FROM Category u WHERE u.name LIKE :name%")
     Category getByName(String name);
     @Query("SELECT u FROM Category u WHERE u.name LIKE :name%")
     List<Category> findByName(String name);


}
