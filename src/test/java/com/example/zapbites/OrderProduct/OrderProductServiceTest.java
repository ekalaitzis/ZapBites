package com.example.zapbites.OrderProduct;

import com.example.zapbites.Evaluators.OrderProductEvaluator;
import com.example.zapbites.OrderProduct.Exceptions.OrderProductNotFoundException;
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

public class OrderProductServiceTest {


    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private OrderProductEvaluator orderProductEvaluator;

    @InjectMocks
    private OrderProductServiceImpl orderProductService;

    private OrderProduct orderProduct1, orderProduct2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        orderProduct1 = new OrderProduct();
        orderProduct1.setId(1L);

        orderProduct2 = new OrderProduct();
        orderProduct2.setId(2L);
    }

    @Test
    void getAllOrderProducts() {
        List<OrderProduct> orderProductList = Arrays.asList(orderProduct1, orderProduct2);
        when(orderProductRepository.findAll()).thenReturn(orderProductList);

        List<OrderProduct> result = orderProductService.getAllOrderProducts();

        assertEquals(orderProductList, result);
        verify(orderProductRepository, times(1)).findAll();
    }

    @Test
    void getOrderProductById() {
        when(orderProductRepository.findById(1L)).thenReturn(Optional.of(orderProduct1));

        Optional<OrderProduct> result = orderProductService.getOrderProductById(1L);

        assertTrue(result.isPresent());
        assertEquals(orderProduct1, result.get());
        verify(orderProductRepository, times(1)).findById(1L);
    }

    @Test
    void createOrderProduct() {
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct1);

        OrderProduct result = orderProductService.createOrderProduct(orderProduct1);

        assertNotNull(result);
        assertEquals(orderProduct1, result);
        verify(orderProductEvaluator, times(1)).checkForOrderProductConditions(orderProduct1);
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    }

    @Test
    void updateOrderProduct() {
        when(orderProductRepository.findAll()).thenReturn(Arrays.asList(orderProduct1, orderProduct2));
        when(orderProductRepository.save(any(OrderProduct.class))).thenReturn(orderProduct1);

        OrderProduct result = orderProductService.updateOrderProduct(orderProduct1);

        assertNotNull(result);
        assertEquals(orderProduct1, result);
        verify(orderProductRepository, times(1)).findAll();
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
    }

    @Test
    void updateOrderProduct_OrderProductNotFoundException() {
        OrderProduct nonExistingOrderProduct = new OrderProduct();
        nonExistingOrderProduct.setId(3L);
        when(orderProductRepository.findAll()).thenReturn(Arrays.asList(orderProduct1, orderProduct2));

        assertThrows(OrderProductNotFoundException.class, () -> orderProductService.updateOrderProduct(nonExistingOrderProduct));
        verify(orderProductRepository, times(1)).findAll();
        verify(orderProductRepository, never()).save(any());
    }

    @Test
    void deleteOrderProductById() {
        orderProductService.deleteOrderProductById(1L);
        verify(orderProductRepository, times(1)).deleteById(1L);
    }
}