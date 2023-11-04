package com.example.ottoservice.controller;

import com.example.ottoservice.model.Product;
import com.example.ottoservice.model.dto.ProductDTO;
import com.example.ottoservice.service.ServiceProductImpl;
import com.example.ottoservice.utils.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@RestController
@Controller

@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    ServiceProductImpl serviceProduct;
    @Autowired
    FileUtility fileUtility;

    public String getIndex(HttpServletRequest request) {
        System.out.println("test");
        return "index.html";
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProductsPaginated(@RequestParam Optional<Long> _end,
                                                    @RequestParam Optional<String> _sort,
                                                    @RequestParam Optional<Long> _start,
                                                    @RequestParam Optional<List<Integer>> id) {
        Collection<Product> allProducts = serviceProduct.getAll();
        Collection<Product> products = id.isPresent() ? allProducts.stream().
                filter(product -> id.get().contains(product.getProduct_id())).collect(Collectors.toList()) : allProducts.stream().collect(Collectors.toList());
        long totalSize = products.size();
        ArrayList<ProductDTO> productDTOs;
        if (_start.isPresent() && _end.isPresent()) {
            long start = _start.get();
            long end = _end.get();
            productDTOs = products.stream().skip(start)
                    .limit(end-start).map(this::getProductDTOfromProduct).collect(Collectors.toCollection(ArrayList::new));
        } else {
            productDTOs = products.stream().map(this::getProductDTOfromProduct).collect(Collectors.toCollection(ArrayList::new));
        }

        return ResponseEntity.ok()
                .headers(getHttpHeadersByCount(totalSize))
                .body(productDTOs);
    }

    private HttpHeaders getHttpHeadersByCount(long size) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Expose-Headers", "X-Total-Count");
        responseHeaders.set("X-Total-Count", String.valueOf(size));
        return responseHeaders;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        Product result = serviceProduct.getEntityById(id);
        return ResponseEntity.ok()
                .body(getProductDTOfromProduct(result));
    }

    @GetMapping("/products/category/{id}/{_start}/{_end}")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@PathVariable int id, @PathVariable long _start, @PathVariable long _end) {
        Collection<Product> result = serviceProduct.getEntityByCategory(id);
        ArrayList<ProductDTO> products = result.stream().skip(_start)
                .limit(_end-_start).map(this::getProductDTOfromProduct).collect(Collectors.toCollection(ArrayList::new));
        return ResponseEntity.ok().
                headers(getHttpHeadersByCount(result.size()))
                .body(products);
    }

    private void handleImageUpdateInFiles(Product previousProduct, ProductDTO newProductDTO) {
        Set<String> imgNamesInDB = getImageNames(previousProduct.getImages());
        Set<ProductDTO.Image> imgFromUI = Arrays.stream(newProductDTO.getPictures()).collect(Collectors.toSet());
        Collection<ProductDTO.Image> imgForCreateList = new ArrayList<>();
        Collection<String> imgForDeleteList = new ArrayList<>();
        filterImages(imgFromUI, imgNamesInDB, imgForCreateList, imgForDeleteList);

        try {
            fileUtility.deleteProductImages(imgForDeleteList, newProductDTO.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (ProductDTO.Image image : imgForCreateList) {
            fileUtility.writeImageFile(image.getSrc(), String.valueOf(newProductDTO.getId()), image.getTitle());
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> editProduct(@PathVariable long id,
                                                 @RequestBody ProductDTO productDTO) {

        // Note preferred way of declaring an array variable
        Product previousProduct = serviceProduct.getEntityById(id);
        prepareImages(productDTO);
        handleImageUpdateInFiles(previousProduct, productDTO);
        serviceProduct.createEntity(getProductFromDTO(productDTO));
        return ResponseEntity.ok()
                .body(new Product());
    }

    private void prepareImages(ProductDTO productDTO) {
        for (ProductDTO.Image img: productDTO.getPictures()) {
            img.setTitle(img.getTitle().replaceAll("\\s+", ""));
        }
    }

    @PostMapping("products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO)  {
        System.out.println(productDTO);
        prepareImages(productDTO);
        Product product = getProductFromDTO(productDTO);
        Product createdProduct = serviceProduct.createEntity(product);
        productDTO.setId(createdProduct.getProduct_id());

        ProductDTO.Image[] images = productDTO.getPictures();
        for (ProductDTO.Image image : images) {
            fileUtility.writeImageFile(image.getSrc(), String.valueOf(createdProduct.getProduct_id()), image.getTitle());
        }

        // Fetching file name
        return ResponseEntity.ok()
                .body(productDTO);
    }

    @DeleteMapping("products/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable long id) {
        Boolean isDeleted = serviceProduct.deleteEntityByID(id);//from database
        if (isDeleted) {
            //delete from files corresponding images
            try {
                fileUtility.deleteProductImageUploadDirectory(id);
            } catch (IOException e) {
                e.printStackTrace();
                isDeleted = false;
            }
        }

        return ResponseEntity.ok().body(isDeleted);
    }

//uploads/products/22/ScreenShot2023-06-13at17.20.33.png
    @GetMapping("/uploads/products/{productId}/{fileName}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String productId, @PathVariable String fileName) throws IOException {
        byte[] image = fileUtility.readImage(productId, fileName);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(image);
    }

    private Product getProductFromDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setProduct_id(productDTO.getId());
        product.setCategoryId(productDTO.getCategoryId());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        StringBuilder images = new StringBuilder();
        for (ProductDTO.Image img: productDTO.getPictures()) {
            images.append(img.getTitle().trim().replace(FileUtility.COMMA, ""));
            images.append(FileUtility.COMMA);
        }
        if (images.length() > 0) {
            images.deleteCharAt(images.length()-1);
        }
        product.setImages(images.toString());
        return product;
    }

    private ProductDTO getProductDTOfromProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategoryId());
        productDTO.setId(product.getProduct_id());
        productDTO.setDescription(product.getDescription());

        String[] imageNames = product.getImages().split(FileUtility.COMMA);

        ProductDTO.Image[] pictures = new ProductDTO.Image[imageNames.length];
        for (int i = 0; i < imageNames.length; i++) {
            String imgName = imageNames[i];
            pictures[i] = new ProductDTO.Image();
            pictures[i].setSrc("http://localhost:8080/uploads/products/" + product.getProduct_id() + "/" + imgName);
            pictures[i].setTitle(imgName);
        }
        productDTO.setPictures(pictures);
        return productDTO;
    }

    private void filterImages(Set<ProductDTO.Image> newImages, Set<String> previousImages,
                              Collection<ProductDTO.Image> imgForCreateList, Collection<String> imgForDeleteList) {
        if (newImages == null){
            return;
        }
        for(ProductDTO.Image newImage: newImages) {
            if (!previousImages.contains(newImage.getTitle())) {
                imgForCreateList.add(newImage);
            }
        }

        Set<String> newImgNames = newImages.stream().map(ProductDTO.Image::getTitle).collect(Collectors.toSet());
        for (String previousImage: previousImages) {
            if (!newImgNames.contains(previousImage)) {
                imgForDeleteList.add(previousImage);
            }
        }
    }

    private Set<String> getImageNames(String images) {
        Set<String> result = new HashSet<>();
        if (images != null) {
            String[] imgArr = images.split(FileUtility.COMMA);
            for (String s : imgArr) {
                result.add(s);
            }
        }
        return result;
    }
}
