package com.course.product.controller;

import com.course.product.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final List<Product> products = new ArrayList<>();
    public ProductController() {
        products.add(new Product("Product 1", 1L, 10.0));
        products.add(new Product("Product 2", 2L, 20.0));
        products.add(new Product("Product 3", 3L, 30.0));
        products.add(new Product("Product 4", 4L, 40.0));
    }

    @GetMapping("/all")
    public List<Product> getProducts() {
        return products;
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
         Optional<Product> product = products.
                stream().
                filter(product1 -> product1.getId().equals(id)).
                findFirst();
                return product.orElse(new Product("Product not found", 0L, 0.0));
    }

}
