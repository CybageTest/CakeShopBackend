package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOrderDao;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class OrderService {

	@Autowired
	private IOrderDao orderDao;

	public Order addOrder(Order order) {
		return orderDao.save(order);
	}
	
	public Order findOrderById(long id) {
		return orderDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order(id) does not exist"));
	}

	public List<Order> getAllOrder() {
		return orderDao.findAll();
	}
	
	public Order deleteOrderById(long id) throws Exception {
		Order orderToDelete = findOrderById(id);
		if (orderToDelete != null) {
			orderDao.deleteById(id);
			return orderToDelete;
		}
		throw new ResourceNotFoundException("Order(id) does not exist");
	}
	
	public List<Order> findCakesByCategory(CakeCategory category) {
		return orderDao.findOrderByCakesCategory(category);
	}
	
}
