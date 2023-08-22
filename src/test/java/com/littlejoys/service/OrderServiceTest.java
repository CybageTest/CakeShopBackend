package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IOrderDao;
import com.littlejoys.dto.OrderDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceNotFoundException;

class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private IOrderDao orderDao;

	private OrderDTO orderDTO;
	private Order order;
	private List<Order> expectedOrders = new ArrayList<>();
	private CakeCategory category = CakeCategory.EGG;
	private Set<Cake> cakes = new HashSet<>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Cake cake1 = new Cake(12356, "lava cake", 123.456, "swwet cake", null, category, null, null);
		Cake cake2 = new Cake(12357, "lava cake", 123.456, "swwet cake", null, category, null, null);
		cakes.add(cake1);
		cakes.add(cake2);
		orderDTO = new OrderDTO(12800, "Test message", 1000.00, 1100.00, "test address", 1, cakes, null, null, null);
		order = new Order(12800, "Test message", 1000.00, 1100.00, "test address", 1, cakes, null, null, null);
		expectedOrders = Arrays.asList(
				new Order(12500, "Test message 1", 1000.00, 1100.00, "test address 1", 1, cakes, null, null, null),
				new Order(12500, "Test message 2", 1000.00, 1100.00, "test address 2", 1, cakes, null, null, null));
	}

	@Test
	void testAddOrder() {
		when(modelMapper.map(any(OrderDTO.class), any())).thenReturn(order);
		when(orderDao.save(any(Order.class))).thenReturn(order);
		Order result = orderService.addOrder(orderDTO);
		assertEquals(order, result);
	}

	@Test
	void testFindOrderById_ValidId_OrderReturned() {
		long orderId = 12356;

		when(orderDao.findById(orderId)).thenReturn(Optional.of(order));
		OrderDTO foundOrderDTO = orderService.findOrderById(orderId);
		OrderDTO expectedOrderDTO = modelMapper.map(order, OrderDTO.class);

		assertEquals(expectedOrderDTO, foundOrderDTO);
		verify(orderDao, times(1)).findById(orderId);
	}

	@Test
	void testWhenIdNotFound_ShouldThrowResourceException() {
		long id = 999;
		Mockito.when(orderDao.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(id));
	}

	@Test
	void testGetAllOrders() {
		when(orderDao.findAll()).thenReturn(expectedOrders);

		List<OrderDTO> orderDTOList = orderService.getAllOrders();

		List<OrderDTO> expectedOrderDtoList = expectedOrders.stream()
				.map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());

		assertEquals(expectedOrderDtoList, orderDTOList);
	}

	@Test
	void testDeleteOrderById() {
		long id = 98654;
		when(orderDao.findById(id)).thenReturn(Optional.of(order));
		OrderDTO mappedOrderDTO = modelMapper.map(order, OrderDTO.class);
		OrderDTO expectedOrderDTO = orderService.deleteOrderById(id);
		assertEquals(expectedOrderDTO, mappedOrderDTO);
	}

	@Test
	void testWhenIdNotFoundForDeleting_ShouldThrowException() {
		long id = 9876;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrderById(id));
		assertEquals("Order(id) does not exist", thrown.getMessage());
	}

	@Test
	void testFindCakesByCategory() {
		when(orderDao.findOrderByCakesCategory(category)).thenReturn(expectedOrders);

		List<Order> result = orderService.findCakesByCategory(category);

		assertEquals(expectedOrders.size(), result.size());

		verify(orderDao, times(1)).findOrderByCakesCategory(category);
	}

}
