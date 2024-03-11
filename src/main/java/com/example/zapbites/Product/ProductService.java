package com.example.zapbites.Product;

import com.example.zapbites.Product.Exceptions.DuplicateProductException;
import com.example.zapbites.Product.Exceptions.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);

    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product updatedProduct) {
        List<Product> allProducts = getAllProducts();

        if (allProducts.stream().anyMatch(p -> p.getId().equals(updatedProduct.getId()))) {
            return productRepository.save(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product with id " + updatedProduct.getId() + " not found.");
        }
    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("the product with id " + id + " not found.", e);
        }
    }
}
