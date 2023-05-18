package com.littlejoys.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.Order;

class IOrderDaoTest {

	@Mock
	private IOrderDao orderDao;

	private Order order1;
	private Order order2;
	private List<Order> orderList = new ArrayList<>();
	private CakeCategory eggCategory = CakeCategory.EGG;
	Set<Cake> eggCakes = new HashSet<>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		Cake eggCake1 = new Cake();
		eggCake1.setCategory(eggCategory);
		Cake eggCake2 = new Cake();
		eggCake2.setCategory(eggCategory);
		eggCakes.add(eggCake1);
		eggCakes.add(eggCake2);
		order1 = new Order();
		order1.setCakes(eggCakes);
		order2 = new Order();
		order2.setCakes(eggCakes);
	}

	@Test
	void testFindOrderByCakesCategory_ExistingCategory_ShouldReturnOrders() {

		when(orderDao.findOrderByCakesCategory(eggCategory)).thenReturn(orderList);

		List<Order> result = orderDao.findOrderByCakesCategory(eggCategory);

		assertNotNull(result);
		assertEquals(orderList.size(), result.size());
	}

}
