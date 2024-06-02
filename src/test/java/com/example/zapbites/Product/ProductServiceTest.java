package com.example.zapbites.Product;

import com.example.zapbites.Product.Exceptions.DuplicateProductException;
import com.example.zapbites.Product.Exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product1, product2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Product2");
    }

    @Test
    void getAllProducts() {
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(productList, result);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));

        Optional<Product> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(product1, result.get());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void createProduct() {
        when(productRepository.findByName("Product1")).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product result = productService.createProduct(product1);

        assertNotNull(result);
        assertEquals(product1, result);
        verify(productRepository, times(1)).findByName("Product1");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_DuplicateProductException() {
        when(productRepository.findByName("Product1")).thenReturn(Optional.of(product1));

        assertThrows(DuplicateProductException.class, () -> productService.createProduct(product1));
        verify(productRepository, times(1)).findByName("Product1");
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void updateProduct() {
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        when(productRepository.save(any(Product.class))).thenReturn(product1);

        Product result = productService.updateProduct(product1);

        assertNotNull(result);
        assertEquals(product1, result);
        verify(productRepository, times(1)).findAll();
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ProductNotFoundException() {
        Product nonExistingProduct = new Product();
        nonExistingProduct.setId(3L);
        nonExistingProduct.setName("NonExistingProduct");
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(nonExistingProduct));
        verify(productRepository, times(1)).findAll();
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }
}