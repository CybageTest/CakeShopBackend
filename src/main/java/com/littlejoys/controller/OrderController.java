package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping("/")
	public ResponseEntity<Order> addOffer(@RequestBody Order order) {
		return new ResponseEntity<Order>(orderService.addOrder(order), HttpStatus.CREATED);
	}
	
	@GetMapping("/allOrders")
	public ResponseEntity<List<Order>> getAllOrders(){
		return new ResponseEntity<List<Order>>(orderService.getAllOrder(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Order> findOrderById(@PathVariable long id){
		return new ResponseEntity<Order>(orderService.findOrderById(id), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/deleteOrder/{id}")
	public ResponseEntity<Order> deleteOrderById(@PathVariable long id) throws Exception{
		return new ResponseEntity<Order>(orderService.deleteOrderById(id), HttpStatus.OK);
	}
	
	@GetMapping("/ordersByCategory/{category}")
	public ResponseEntity<List<Order>> getOrdersByCakeCategory(@PathVariable CakeCategory category){
		return new ResponseEntity<List<Order>>(orderService.findCakesByCategory(category), HttpStatus.FOUND);
	}
	
}
