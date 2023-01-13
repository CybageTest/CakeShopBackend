package com.littlejoys.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOrderDao;
import com.littlejoys.dto.OrderDTO;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class OrderService {

	public static final Logger logger = Logger.getLogger(OrderService.class);

	@Autowired
	private IOrderDao orderDao;

	@Autowired
	private ModelMapper modelMapper;

	public OrderDTO addOrder(OrderDTO orderDTO) {
		Order order = modelMapper.map(orderDTO, Order.class);
		Order savedOrder = orderDao.save(order);
		logger.info("Order added: " + savedOrder);
		return modelMapper.map(savedOrder, OrderDTO.class);
	}

	public OrderDTO findOrderById(long id) {
		OrderDTO orderDTO = modelMapper.map(orderDao.findById(id), OrderDTO.class);
		if (orderDTO != null) {
			return orderDTO;
		} else {
			throw new ResourceNotFoundException("Order(id) does not exist");
		}
	}

	public List<OrderDTO> getAllOrders() {
		List<OrderDTO> orderDTOList = new ArrayList<>();
		List<Order> orderList = orderDao.findAll();
		for (Order order : orderList) {
			OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
			orderDTOList.add(orderDTO);
		}
		return orderDTOList;
	}

	public OrderDTO deleteOrderById(long id) throws ResourceNotFoundException {
		OrderDTO orderToDelete = findOrderById(id);
		if (orderToDelete != null) {
			orderDao.deleteById(id);
			logger.info("Deleted order: " + orderToDelete + " for ID: " + id);
			return orderToDelete;
		}
		throw new ResourceNotFoundException("Order(id) does not exist");
	}

	public List<Order> findCakesByCategory(CakeCategory category) {
		return orderDao.findOrderByCakesCategory(category);
	}

}
