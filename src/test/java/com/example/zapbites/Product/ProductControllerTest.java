package com.example.zapbites.Product;

import com.example.zapbites.Category.Category;
import com.example.zapbites.Product.Exceptions.DuplicateProductException;
import com.example.zapbites.Product.Exceptions.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(1L, "Test Product", "Description", new Category(), new ArrayList<>());
        testProduct.setPrice(BigDecimal.valueOf(10.99)); // Example price
    }

    @Test
    @DisplayName("Should return a list of all products")
    void getAllProducts_ShouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(testProduct);
        when(productService.getAllProducts()).thenReturn(productList);

        ResponseEntity<List<Product>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productList, responseEntity.getBody());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    @DisplayName("Should return a product when a valid id is given")
    void getProductById_WithValidId_ShouldReturnProduct() {
        when(productService.getProductById(testProduct.getId())).thenReturn(Optional.of(testProduct));

        ResponseEntity<Product> responseEntity = productController.getProductById(testProduct.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProduct, responseEntity.getBody());
        verify(productService, times(1)).getProductById(testProduct.getId());
    }

    @Test
    @DisplayName("Should return not found when an invalid id is given")
    void getProductById_WithInvalidId_ShouldReturnNotFound() {
        Long productId = 99L;
        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<Product> responseEntity = productController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productService, times(1)).getProductById(productId);
    }

    @Test
    @DisplayName("Should create a product when valid attributes are given")
    void createProduct_WithValidProduct_ShouldReturnCreatedProduct() {
        when(productService.createProduct(testProduct)).thenReturn(testProduct);

        ResponseEntity<Object> responseEntity = productController.createProduct(testProduct);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(testProduct, responseEntity.getBody());
        verify(productService, times(1)).createProduct(testProduct);
    }

    @Test
    @DisplayName("Should return bad request when trying to create a duplicate product")
    void createProduct_WithDuplicateProduct_ShouldReturnBadRequest() {
        when(productService.createProduct(testProduct)).thenThrow(new DuplicateProductException(""));

        ResponseEntity<Object> responseEntity = productController.createProduct(testProduct);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        verify(productService, times(1)).createProduct(testProduct);
    }

    @Test
    @DisplayName("Should update a product when valid attributes are given")
    void updateProduct_WithValidAttributes_ShouldReturnUpdatedProduct() {
        when(productService.updateProduct(testProduct)).thenReturn(testProduct);

        ResponseEntity<Product> responseEntity = productController.updateProduct(testProduct);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(testProduct, responseEntity.getBody());
        verify(productService, times(1)).updateProduct(testProduct);
    }

    @Test
    @DisplayName("Should return not found when trying to update a non-existing product")
    void updateProduct_WithNonExistingId_ShouldReturnNotFound() {
        when(productService.updateProduct(testProduct)).thenThrow(new ProductNotFoundException(""));

        ResponseEntity<Product> responseEntity = productController.updateProduct(testProduct);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(productService, times(1)).updateProduct(testProduct);
    }

    @Test
    @DisplayName("Should delete a product")
    void deleteProductById_WithValidId_ShouldReturnNoContent() {
        Long productId = 1L;

        ResponseEntity<Void> responseEntity = productController.deleteProductById(productId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productService, times(1)).deleteProduct(productId);
    }
}
