package com.example.zapbites.Product;

import com.example.zapbites.Product.Exceptions.DuplicateProductException;
import com.example.zapbites.Product.Exceptions.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);

    }
    @Override
    public Product createProduct(Product product) {
        String name = product.getName();
        Optional<Product> existingProduct = productRepository.findByName(name);

        if (existingProduct.isPresent()) {
            throw new DuplicateProductException("Product: " + name + " already exists.");
        }
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(Product updatedProduct) {
        List<Product> allProducts = getAllProducts();

        if (allProducts.stream().anyMatch(p -> p.getId().equals(updatedProduct.getId()))) {
            return productRepository.save(updatedProduct);
        } else {
            throw new ProductNotFoundException("Product " + updatedProduct.getName() + " not found.");
        }
    }
    @Override
    public void deleteProduct(Long id) {
            productRepository.deleteById(id);
    }
}
