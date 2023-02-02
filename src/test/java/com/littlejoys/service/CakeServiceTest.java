package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.demo.CakeShopApplication;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;

@SpringBootTest(classes = CakeShopApplication.class)
class CakeServiceTest {

	@Autowired
	private CakeService cakeService;

	@MockBean
	private ICakeDao cakeDao;

	@BeforeEach
	void setup() {
		Optional<Cake> dummyCake = Optional.of(new Cake(1, "lava cake", 123.456, "swwet cake", 1,
				CakeFlavours.CHOCOLATE, CakeCategory.EGG, CakeOccasions.BIRTHDAY, null));
		Mockito.when(cakeDao.findById((long) 1)).thenReturn(dummyCake);
	}

//	@Test
//	void getCakeByIdTest() {
//		long id = 1;
//		Cake catchedCake = cakeService.findCakeById(id);
//		assertEquals(id, catchedCake.getId());
//	}

}
