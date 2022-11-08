package com.littlejoys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOrderDao;
import com.littlejoys.entity.Order;

@Service
public class OrderService {

	@Autowired
	private IOrderDao orderDao;

	public Order addOrder(Order order) {
		return orderDao.save(order);
	}

}
