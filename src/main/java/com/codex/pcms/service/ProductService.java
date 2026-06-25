package com.codex.pcms.service;

import com.codex.pcms.model.Product;
import com.codex.pcms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> search(String keyword) {
        return productRepository.findAll(keyword);
    }

    public List<Product> lowStock() {
        return productRepository.findLowStock();
    }

    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    public Product save(Product product) {
        long id = productRepository.save(product);
        return get(id);
    }

    public void update(Product product) {
        productRepository.update(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
