package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.dto.OrderDTO;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceNotFoundException;
import com.littlejoys.service.OrderService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/")
	public ResponseEntity<Order> addOrder(@RequestBody OrderDTO orderDTO) {
		return new ResponseEntity<>(orderService.addOrder(orderDTO), HttpStatus.CREATED);
	}

	@GetMapping("/allOrders")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		return new ResponseEntity<>(orderService.getAllOrders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> findOrderById(@PathVariable long id) {
		return new ResponseEntity<>(orderService.findOrderById(id), HttpStatus.OK);
	}

	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<OrderDTO> deleteOrderById(@PathVariable long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(orderService.deleteOrderById(id), HttpStatus.OK);
	}

	@GetMapping("/ordersByCategory/{category}")
	public ResponseEntity<List<Order>> getOrdersByCakeCategory(@PathVariable CakeCategory category) {
		return new ResponseEntity<>(orderService.findCakesByCategory(category), HttpStatus.OK);
	}

}
