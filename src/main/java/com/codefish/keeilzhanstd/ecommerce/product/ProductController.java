package com.codefish.keeilzhanstd.ecommerce.product;

import com.codefish.keeilzhanstd.ecommerce.product.Product;
import com.codefish.keeilzhanstd.ecommerce.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ecommerce/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {
        return productService.getOne(id);
    }

    @PostMapping("/add")
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/edit/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
}
