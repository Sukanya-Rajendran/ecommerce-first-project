package com.ecomapp.ecomapp.controller.admincontroller.product;


import com.ecomapp.ecomapp.model.Category;
import com.ecomapp.ecomapp.model.Image;
import com.ecomapp.ecomapp.model.Product;
import com.ecomapp.ecomapp.model.User;
import com.ecomapp.ecomapp.repository.ProductRepo.ProductRepository;
import com.ecomapp.ecomapp.service.Category.CategoryService;
import com.ecomapp.ecomapp.service.ProductService.ProductService;
import com.ecomapp.ecomapp.service.image.ImageService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Controller
@RequestMapping("/product")
public class AdminProductController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String listProducts(Model model,
                               @RequestParam(name = "field", required = false, defaultValue = "productName") String field,
                               @RequestParam(name = "sort", required = false, defaultValue = "DESC") String sort,
                               @RequestParam(name = "page", required = false, defaultValue = "0") int page,
                               @RequestParam(name = "size", required = false, defaultValue = "50") int size,
                               @RequestParam(name = "keyword", required = false) String keyword,
                               @RequestParam(name = "filter", required = false, defaultValue = "") String filter) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort), field));

        Page<Product> products = Page.empty();

        if (keyword == null || keyword.equals("")) {
            products = productService.findAll(pageable);
        } else {
            products = productService.findByName(pageable, keyword);
       }
//        List<Product>product =productService.getAll();
//        Map<UUID,List<String>> productimagesmap = new HashMap<>();
//        for(Product pro : product){
//            List<String> productimages = productService.getProductImages(pro.getId());
//            productimagesmap.put(pro.getId(),productimages);
//        }
//        List<String> imagepath = new ArrayList<>();
//        for(Image image : pro)
        model.addAttribute("products", products);
//        model.addAttribute("productimages",productimagesmap);
//        model.addAttribute("images",)

        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("field", field);
        model.addAttribute("sort", sort);
        model.addAttribute("pageSize", size);
        int startPage = Math.max(0, page - 1);
        int endPage = Math.min(page + 1, products.getTotalPages() - 1);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        model.addAttribute("empty", products.getTotalElements() == 0);

        return "/admin/product/product-management";
    }

    @GetMapping("/create")
    public String index(Model model) {

        List<Category> categoryList = categoryService.findAll();

        model.addAttribute("categories", categoryList);
        model.addAttribute("product", new Product());

        return "/admin/product/create-product";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable String id, Model model) {

        Optional<Product> product = productService.findById(UUID.fromString(id));
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        model.addAttribute("categories", categoryService.findAll());
            return "/admin/product/edit-product";
        }

        return "product/";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable UUID id, Model model, RedirectAttributes attributes) {
        productService.deleteById(id);
        attributes.addFlashAttribute("message", "Product deleted successfully");
        return "redirect:/product";
    }

    @GetMapping("/toggleStatus/{id}")
    public String toggleStatus(@PathVariable UUID id) {
        productService.findById(id)
                .ifPresent(product -> {
                    product.setEnabled(!product.isEnabled());
                    productService.save(product);
                });
        return "redirect:/product";
    }

    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") @Valid Product product,
                                BindingResult result,
                                @RequestParam(value = "deletedImages", required = false) List<String> deletedImages,
                                @RequestParam(value = "newImages", required = false) List<MultipartFile> newImages,
                                Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "admin/product/update-product";
        }

        // Update the product details
        if (product.getId() == null) {
            return "redirect:/product";
        }

//        existingProduct.setProductName(product.getProductName());
//        existingProduct.setCategory(product.getCategory());


        // Handle deleted images
        if (deletedImages != null && !deletedImages.isEmpty()) {
            for (String imageId : deletedImages) {
                imageService.deleteImageById(UUID.fromString(imageId));
            }
        }

        // Handle new images
        if (newImages != null && !newImages.isEmpty()) {
            for (MultipartFile newImage : newImages) {
                String fileLocation = handleFileUpload(newImage); // Save the new image and get its file location
                Image imageEntity = new Image(fileLocation, product); // Create an Image entity with the file location
                imageEntity = imageService.saveImage(imageEntity);
                if (product.getImages() == null) {
                    product.setImages(new ArrayList<>());
                }
                product.getImages().add(imageEntity); // Add the Image entity to the Product's list of images
            }
        }

        productService.save(product);

        return "redirect:/product";
    }

    @GetMapping("/productAddPage")
    public String getProductAddPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        return "/admin/product/create-product";
    }
    @PostMapping("/addproduct")
    public String addproduct(@ModelAttribute Product product,
                             @RequestParam("imagefiles")List<MultipartFile> imagefiles){
                List<byte[]> addproduct =new ArrayList<>();
                for(MultipartFile  file : imagefiles){
                    try{
                        addproduct.add(file.getBytes());
                    }catch(IOException ex){
                        System.out.println("error occured");
                        continue;
                    }
                }
                product.setImagefiles(addproduct);
                productService.save(product);
                return "redirect:/product";
    }


    @PostMapping("/save")
    public String saveProduct(@ModelAttribute Product product,
                              BindingResult result,
                              @RequestParam("images") List<MultipartFile> imageFiles,
                              Model model) throws IOException {

        Optional<Product> existingProduct = productService.findByName(product.getProductName());
        if (existingProduct.isPresent()) {
            result.rejectValue("productName", "error.productName", "Product already exists");
            model.addAttribute("categories", categoryService.findAll());
            return "redirect:/product";
        }

        productService.save(product);



        List<Image> images = new ArrayList<>();
        if (!imageFiles.get(0).getOriginalFilename().equals("")) {
            for (MultipartFile image : imageFiles) {
                System.out.println(image.toString());
                String fileLocation = handleFileUpload(image); // Save the image and get its file location
                Image imageEntity = new Image(fileLocation, product); // Create an Image entity with the file location
                imageEntity = imageService.saveImage(imageEntity);
                images.add(imageEntity); // Add the Image entity to the Product's list of images
            }
        }

        return "redirect:/product";
    }


    private String handleFileUpload(MultipartFile file) throws IOException {
        // Define the directory to save the file in
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/images/product";

        // Create the directory if it doesn't exist
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // Generate a unique file name for the uploaded file
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        // Save the file to the upload directory
        String filePath = uploadDir + "/" + fileName;
        Path path = Paths.get(filePath);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        System.gc();

        // Return the file path
        return fileName;
    }

    private void handleDelete(String fileName) throws IOException {
        // Define the directory
        String rootPath = System.getProperty("user.dir");
        String uploadDir = rootPath + "/src/main/resources/static/images/product";

        // Get the file path
        String filePath = uploadDir + "/" + fileName;

        // Create a file object for the file to be deleted
        File file = new File(filePath);

        // Check if the file exists
        if (file.exists()) {
            // Delete the file
            file.delete();
            System.out.println("File deleted successfully!");
        } else {
            System.out.println("File not found!");
        }
    }

    @GetMapping("/searchproduct")
    public String searchCategoryByName(@RequestParam("productName") String productName, Model model) {
        List<Product> products = productService.findByProductNameContaining(productName);
        System.out.println(products);
        model.addAttribute("products", products);
        return "/admin/product/product-management";
    }
        @GetMapping("/toggle-list/{id}")
    public String deleteProduct(@PathVariable("id") UUID  id) {
        productService.toggleTheProduct(id);
        return "redirect:/admin/product";

    }
}


//    @GetMapping("/productSearch")
//    public String searchUsers(@RequestParam("queryType") String queryType,
//                              @RequestParam("queryWord") String query,
//                              Model model) {
//
//        List<Product> product = new ArrayList<>();
//
//        if("productName".equals(queryType)){
//            product = productRepository.findByProductNameContaining(query);
////        } else if ("lastName".equals(queryType)) {
////            product = userRepository.findByLastNameContaining(query);
////        } else if ("email".equals(queryType)) {
////            product = userRepository.findByEmailContaining(query);
//       }
//
//        for (Product Products : product) {
//            System.out.println("product not Found: " + Products.getProductName() + " ");
//        }
//        if(product.isEmpty()){
//            return "no_product_found";
//        }
//        System.out.println("printing product ," + product);
//        model.addAttribute("listSearchProductName",product);
//        return "admin/product/searchManagerProduct";
//
//    }




