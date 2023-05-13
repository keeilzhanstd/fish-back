package com.codefish.keeilzhanstd.ecommerce.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getOne(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        Optional<Product> existing = productRepository.findById(id);
        if (existing.isPresent()) {
            Product updated = existing.get();
            updated.setName(product.getName());
            updated.setCategory(product.getCategory());
            updated.setInStock(product.getInStock());
            updated.setOrders(product.getOrders());
            return productRepository.save(updated);
        }
        return null;
    }

}