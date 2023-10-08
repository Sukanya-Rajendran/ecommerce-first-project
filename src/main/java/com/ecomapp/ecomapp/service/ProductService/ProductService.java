package com.ecomapp.ecomapp.service.ProductService;

import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Image;
import com.ecomapp.ecomapp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {


    Product getByName(String productName);

    void  save(Product product);


    List<Product> getAll();

    Optional<Product> findById(UUID id);

    boolean existsByName(String productName);


    void deleteById(UUID id);

    Optional<Product> findByName(String productName);

    void deleteImage(Image deletedImage);

    void deleteImageById(UUID imageId);

    List<Product> findAll();

    Optional<Product> getProductById(UUID id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByName(Pageable pageable, String keyword);

    Page<Product> getByNamePaged(String keyword, Pageable pageable);

    List<Product> findByProductNameContaining(String query);

    public void toggleTheProduct(UUID id);

    public List<String> getProductImages(UUID productId);

    public void saveProductImages(UUID productId, List<MultipartFile> imageFiles);









}




