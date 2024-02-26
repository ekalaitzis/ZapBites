package com.example.zapbites.OrderProduct;

import com.example.zapbites.OrderProduct.Exceptions.DuplicateOrderProductException;
import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
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
public class OrderProductServiceTest {

    @Mock
    private OrderProductRepository orderProductRepository;

    @InjectMocks
    private OrderProductService orderProductService;

    @Test
    void getAllOrderProducts_ShouldReturnListOfOrderProducts() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct());
        orderProductList.add(new OrderProduct());
        when(orderProductRepository.findAll()).thenReturn(orderProductList);

        List<OrderProduct> result = orderProductService.getAllOrderProducts();

        assertEquals(2, result.size());
    }

    @Test
    void getOrderProductById_WithValidId_ShouldReturnOrderProduct() {
        Long orderProductId = 1L;
        OrderProduct orderProduct = new OrderProduct();
        when(orderProductRepository.findById(orderProductId)).thenReturn(Optional.of(orderProduct));

        Optional<OrderProduct> result = orderProductService.getOrderProductById(orderProductId);

        assertTrue(result.isPresent());
        assertEquals(orderProduct, result.get());
    }

    @Test
    void getOrderProductById_WithInvalidId_ShouldReturnEmptyOptional() {
        Long orderProductId = 1L;
        when(orderProductRepository.findById(orderProductId)).thenReturn(Optional.empty());

        Optional<OrderProduct> result = orderProductService.getOrderProductById(orderProductId);

        assertTrue(result.isEmpty());
    }

    @Test
    void createOrderProduct_WithValidOrderProduct_ShouldReturnCreatedOrderProduct() {
        OrderProduct orderProduct = new OrderProduct();
        when(orderProductRepository.findById(any())).thenReturn(Optional.empty());
        when(orderProductRepository.save(orderProduct)).thenReturn(orderProduct);

        OrderProduct result = orderProductService.createOrderProduct(orderProduct);

        assertEquals(orderProduct, result);
    }

    @Test
    void createOrderProduct_WithDuplicateId_ShouldThrowDuplicateOrderProductException() {
        OrderProduct orderProduct = new OrderProduct();
        when(orderProductRepository.findById(any())).thenReturn(Optional.of(new OrderProduct()));

        assertThrows(DuplicateOrderProductException.class, () -> orderProductService.createOrderProduct(orderProduct));
    }

    @Test
    void updateOrderProduct_WithValidOrderProduct_ShouldReturnUpdatedOrderProduct() {
        OrderProduct updatedOrderProduct = new OrderProduct();
        updatedOrderProduct.setId(1L);
        // Mocking getAllOrderProducts() to return a list containing the updated orderProduct
        when(orderProductService.getAllOrderProducts()).thenReturn(Collections.singletonList(updatedOrderProduct));
        when(orderProductRepository.save(updatedOrderProduct)).thenReturn(updatedOrderProduct);

        OrderProduct result = orderProductService.updateOrderProduct(updatedOrderProduct);

        assertEquals(updatedOrderProduct, result);
    }

    @Test
    void updateOrderProduct_WithNonExistingId_ShouldThrowOrderProductNotFoundException() {
        OrderProduct updatedOrderProduct = new OrderProduct();
        // Mocking getAllOrderProducts() to return an empty list, simulating that the orderProduct does not exist
        when(orderProductService.getAllOrderProducts()).thenReturn(Collections.emptyList());

        assertThrows(OrderProductNotFoundException.class, () -> orderProductService.updateOrderProduct(updatedOrderProduct));
    }

    @Test
    void deleteOrderProductById_WithValidId_ShouldDeleteOrderProduct() {
        Long orderProductId = 1L;

        orderProductService.deleteOrderProductById(orderProductId);

        verify(orderProductRepository, times(1)).deleteById(orderProductId);
    }

    @Test
    void deleteOrderProductById_WithNonExistingId_ShouldThrowOrderProductNotFoundException() {
        Long orderProductId = 1L;
        doThrow(new EmptyResultDataAccessException(1)).when(orderProductRepository).deleteById(orderProductId);

        assertThrows(OrderProductNotFoundException.class, () -> orderProductService.deleteOrderProductById(orderProductId));
    }
}
