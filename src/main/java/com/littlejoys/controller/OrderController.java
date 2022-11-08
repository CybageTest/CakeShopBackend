package com.littlejoys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
