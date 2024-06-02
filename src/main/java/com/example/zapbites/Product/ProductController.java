package com.example.zapbites.Product;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasRole('BUSINESS')") //this is not secured properly as it is not going to be used in production
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByProductId(#id)")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        return optionalProduct.map(product -> new ResponseEntity<>(product, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByProduct(#product)")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@businessOwnerEvaluator.checkForOwnerByProductId(#id)")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
