package com.ecomapp.ecomapp.controller.admincontroller.category;

import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;    
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String showHome(Model model) {
        List<Category> categories = categoryService.findAll();

        model.addAttribute("category", categories);
        model.addAttribute("categories", categories);
        return "admin/category/category";
    }

    @PostMapping("/addcategory")
    public String addCategory(@ModelAttribute("category") Category category, BindingResult result, Model model) {
        String name = category.getName();
        Category existingCategory = categoryService.getByName(name);
        if (existingCategory != null && existingCategory.getIsAvailable()) {
            existingCategory.setAvailable(false);
            categoryService.updateCategory(existingCategory);
        } else if (existingCategory != null && !existingCategory.getIsAvailable()) {
            result.rejectValue("categoryName", "error.categoryName", "Category Already Exists");
            return "redirect:admin/category";

        } else {
            category.setAvailable(false);
            categoryService.addCategory(category);
        }


        return "redirect:/admin/category"; // Corrected the return view name
    }

    @GetMapping("/showaddcategory")
    public String showaddcategroy(Model model) {
        model.addAttribute("category", new Category());
//      model.addAttribute("successmesage","Data save succesfully");
        System.out.println("hi");
        return "/admin/category/categoryadd";


    }

//    @GetMapping("/showlistmanageCategories")
//    public String listCateogry(){
//        return "admin/category/";
//    }


    @GetMapping("/listmanageCategories")
    public String manageCategories(Model model) {
        // Add an individual category to the model (replace 'categoryId' with the actual category ID you want to add)
        Category category = (Category) categoryService.findAll();
        System.out.println(category);
        model.addAttribute("category", category);


        return "/admin/category/listmanageCategories"; // Return the appropriate view for managing categories
    }


    @GetMapping("/toggle-list/{id}")
    public String deleteCategory(@PathVariable("id") int  id) {
        categoryService.toggleTheCategory(id);
        return "redirect:/admin/category";

    }

    @GetMapping("/categories/update/{id}")
    public String updateCategory(@PathVariable("id") int id, Model model) {
        Optional<Category> category = categoryService.getCategoryByid(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "/admin/category";
        } else {
            return "/error";
        }
    }
    @GetMapping("/searchCategories")
    public String searchCategoryByName(@RequestParam ("name") String name , Model model){
        List<Category> categories = categoryService.getCategoriesByName(name);
        System.out.println(categories);
        model.addAttribute("categories" , categories);
        return "/admin/category/category";
    }

}



//    @PostMapping("/save")
//    public String saveCategory(@ModelAttribute Category category, Model model, BindingResult result) {
//        String name = category.getCategoryName();
//        Category existingCategory = categoryService.getByCategoryName(name);
//
//        if(existingCategory!=null && existingCategory.isDeleted()){
//            existingCategory.setDeleted(false);
//            categoryService.updateCategory(existingCategory);
//        } else if (existingCategory!=null && !existingCategory.isDeleted()) {
//            result.rejectValue("categoryName","error.categoryName","Category Already Exists");
//            return "admin/categorylist";
//
//        }
//        else {
//            category.setDeleted(false);
//            categoryService.addCategory(category);
//        }
//
////        if (existingCategory != null && !existingCategory.isDeleted() ) {
////            result.rejectValue("categoryName", "error.categoryName", "Category Already Exists");
////            return "admin/categorylist"; // Corrected the return view name
////        }
//
//
////        categoryService.addCategory(category);
//
//        return "redirect:/category/create"; // Corrected the return view name
//    }