package com.littlejoys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.littlejoys.dto.OrderDTO;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceNotFoundException;
import com.littlejoys.service.OrderService;

class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private OrderService orderService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddOrder() {
		OrderDTO orderDTO = new OrderDTO();
		Order createdOrder = new Order();
		Mockito.when(orderService.addOrder(orderDTO)).thenReturn(createdOrder);

		ResponseEntity<Order> expectedResponse = new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
		ResponseEntity<Order> actualResponse = orderController.addOrder(orderDTO);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(createdOrder, actualResponse.getBody());
		Mockito.verify(orderService, Mockito.times(1)).addOrder(orderDTO);
	}

	@Test
	void testGetAllOrders() {
		List<OrderDTO> expectedOrders = new ArrayList<>();
		Mockito.when(orderService.getAllOrders()).thenReturn(expectedOrders);

		ResponseEntity<List<OrderDTO>> response = orderController.getAllOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOrders, response.getBody());
		Mockito.verify(orderService, Mockito.times(1)).getAllOrders();
	}

	@Test
	void testFindOrderById() {
		long orderId = 1L;
		OrderDTO expectedOrderDTO = new OrderDTO();
		Mockito.when(orderService.findOrderById(orderId)).thenReturn(expectedOrderDTO);

		ResponseEntity<OrderDTO> response = orderController.findOrderById(orderId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOrderDTO, response.getBody());
		Mockito.verify(orderService, Mockito.times(1)).findOrderById(orderId);
	}

	@Test
	void testDeleteOrderById() throws ResourceNotFoundException {
		long orderId = 1L;
		OrderDTO expectedOrderDTO = new OrderDTO();
		Mockito.when(orderService.deleteOrderById(orderId)).thenReturn(expectedOrderDTO);

		ResponseEntity<OrderDTO> response = orderController.deleteOrderById(orderId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOrderDTO, response.getBody());
		Mockito.verify(orderService, Mockito.times(1)).deleteOrderById(orderId);
	}

	@Test
	void testGetOrdersByCakeCategory() {
		CakeCategory category = CakeCategory.EGG;
		List<Order> expectedOrders = new ArrayList<>();
		Mockito.when(orderService.findCakesByCategory(category)).thenReturn(expectedOrders);

		ResponseEntity<List<Order>> response = orderController.getOrdersByCakeCategory(category);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOrders, response.getBody());
		Mockito.verify(orderService, Mockito.times(1)).findCakesByCategory(category);
	}

}
