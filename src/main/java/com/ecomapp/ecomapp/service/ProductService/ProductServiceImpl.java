package com.ecomapp.ecomapp.service.ProductService;



import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Image;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.repository.ImageRepository;
import com.ecomapp.ecomapp.repository.ProductRepo.ProductRepository;
import com.ecomapp.ecomapp.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageService imageService;

    @Autowired
    ImageRepository imageRepository;


    @Override
    public Product getByName(String productName) {
        return productRepository.findByProductNameLike("%"+productName+"%");
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public boolean existsByName(String productName) {
        return productRepository.existsByProductName(productName);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findByName(String productName) {
        return Optional.ofNullable(productRepository.findByProductName(productName));
    }


    @Override
    public void deleteImage(Image deletedImage) {
        // Delete the image from the image database
        imageService.deleteImage(deletedImage);

        // Remove the image from the product
        Product product = deletedImage.getProduct();
        product.getImages().remove(deletedImage);
        save(product);
    }

    public void deleteImageById(UUID imageId) {
        Optional<Image> image = imageRepository.findById(imageId);
        if (image.isPresent()) {
            Optional<Product> product = productRepository.findById(image.get().getProduct().getId());
            if (product.isPresent()) {
                product.get().getImages().removeIf(img -> img.getId().equals(imageId));
                productRepository.save(product.get());
            }
            imageRepository.deleteById(imageId);
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByName(Pageable pageable, String keyword) {
        return productRepository.findByProductName(pageable, keyword);
    }

    @Override
    public Page<Product> getByNamePaged(String keyword, Pageable pageable) {
        return productRepository.findByProductNameLike("%"+keyword+"%", pageable);
    }

    @Override
    public List<Product> findByProductNameContaining(String query) {

        return productRepository.findByProductNameContaining(query);
    }

    @Override
    public void toggleTheProduct(UUID id) {
        Optional<Product>optionalProduct =productRepository.findById(id);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setAvailable(!product.getIsAvailable());
            System.out.println(product);
            productRepository.save(product);
        }
    }

    @Override
    public List<String> getProductImages(UUID productId) {
        Product product = productRepository.findById(productId).orElse(null) ;
        if (product != null) {
            List<byte[]> images = product.getImagefiles();
            return convertImagesToBase64(images);
        }
        return null;
    }


    @Override
    public void saveProductImages(UUID productId, List<MultipartFile> imageFiles) {
        Product product = productRepository.findById(productId).orElse(null) ;
        if (product != null && imageFiles != null && !imageFiles.isEmpty()) {
            List<byte[]> images = product.getImagefiles();

            for (MultipartFile file : imageFiles) {
                try {
                    if (!file.isEmpty()) {
                        images.add(file.getBytes());
                    }
                } catch (IOException e) {
                    System.out.println("inside save product image exception block");
                }
            }

            productRepository.save(product);
        }
    }

    private List<String> convertImagesToBase64(List<byte[]> images) {
        return images.stream()
                .map(image -> Base64.getEncoder().encodeToString(image))
                .collect(Collectors.toList());
    }


}






