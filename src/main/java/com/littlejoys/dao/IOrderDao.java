package com.littlejoys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;

@Repository
public interface IOrderDao extends JpaRepository<Order, Long>{
	List<Order> findOrderByCakesCategory(CakeCategory category);
}
