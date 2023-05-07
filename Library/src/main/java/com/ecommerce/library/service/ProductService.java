package com.ecommerce.library.service;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDto> findALl();
    Product save(MultipartFile imageProduct, Product productDto);
    Product update(Product productDto);
    void deleteById(Long id);
    void enableById(Long id);
}
