package com.littlejoys.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOrderDao;
import com.littlejoys.dto.OrderDTO;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceCannotBeNullException;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class OrderService {

	public static final Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private ModelMapper modelMapper;

	public Order addOrder(OrderDTO orderDTO) {
		if (orderDTO == null) {
			throw new ResourceCannotBeNullException("Order cannot be empty");
		}
		Order orderToBeSaved = modelMapper.map(orderDTO, Order.class);
		return orderDao.save(orderToBeSaved);
	}

	public OrderDTO findOrderById(long id) {
		Optional<Order> orderOptional = orderDao.findById(id);
		if (orderOptional.isPresent()) {
			Order orderToFind = orderOptional.get();
			return modelMapper.map(orderToFind, OrderDTO.class);
		} else {
			throw new ResourceNotFoundException("Order(id) does not exist");
		}
	}

	public List<OrderDTO> getAllOrders() {
		List<Order> orderList = orderDao.findAll();
		return orderList.stream().map(order -> modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
	}

	public OrderDTO deleteOrderById(long id) throws ResourceNotFoundException {
		Optional<Order> orderOptional = orderDao.findById(id);
		if (orderOptional.isPresent()) {
			Order orderToDelete = orderOptional.get();
			orderDao.deleteById(id);
			logger.info("Deleted offer: " + orderToDelete + " for ID: " + id);
			return modelMapper.map(orderToDelete, OrderDTO.class);
		} else {
			throw new ResourceNotFoundException("Order(id) does not exist");
		}
	}

	public List<Order> findCakesByCategory(CakeCategory category) {
		return orderDao.findOrderByCakesCategory(category);
	}

}
