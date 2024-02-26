package com.example.zapbites.Product;

import com.example.zapbites.Product.Exceptions.DuplicateProductException;
import com.example.zapbites.Product.Exceptions.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_ShouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(2, result.size());
    }

    @Test
    void getProductById_WithValidId_ShouldReturnProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(product, result.get());
    }

    @Test
    void getProductById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createProduct_WithValidProduct_ShouldReturnCreatedProduct() {
        Product product = new Product();
        when(productRepository.findById(any())).thenReturn(Optional.empty());
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.createProduct(product);

        assertEquals(product, result);
    }

    @Test
    void createProduct_WithDuplicateId_ShouldThrowDuplicateProductException() {
        Product product = new Product();
        when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

        assertThrows(DuplicateProductException.class, () -> productService.createProduct(product));
    }

    @Test
    void updateProduct_WithValidProduct_ShouldReturnUpdatedProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        // Mocking getAllProducts() to return a list containing the updated product
        when(productService.getAllProducts()).thenReturn(Collections.singletonList(updatedProduct));
        when(productRepository.save(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(updatedProduct);

        assertEquals(updatedProduct, result);
    }

    @Test
    void updateProduct_WithNonExistingId_ShouldThrowProductNotFoundException() {
        Product updatedProduct = new Product();
        // Mocking getAllProducts() to return an empty list, simulating that the product does not exist
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(updatedProduct));
    }

    @Test
    void deleteProduct_WithValidId_ShouldDeleteProduct() {
        Long productId = 1L;

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProduct_WithNonExistingId_ShouldThrowProductNotFoundException() {
        Long productId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(productRepository).deleteById(productId);

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
    }
}
