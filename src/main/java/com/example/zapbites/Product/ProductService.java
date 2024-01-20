package com.example.zapbites.Product;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public Product updateProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (DataAccessException e) {
            throw new EntityNotFoundException("the product with id " + product.getId() + " not found.", e);
        }

    }

    public void deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("the product with id " + id + " not found.", e);
        }
    }
}
