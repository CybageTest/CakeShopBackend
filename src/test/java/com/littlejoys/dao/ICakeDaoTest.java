package com.littlejoys.dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;

class ICakeDaoTest {

	@Mock
	private ICakeDao cakeDao;

	private Cake cake1;
	private Cake cake2;
	private CakeCategory category;
	private CakeOccasions occasions;
	private CakeFlavours flavours;
	List<Cake> expectedCakes = new ArrayList<>();

	public ICakeDaoTest() {
		MockitoAnnotations.openMocks(this);
	}

	@BeforeEach
	void setUp() throws Exception {
		category = CakeCategory.EGG;
		occasions = CakeOccasions.SPECIAL_DAYS;
		flavours = CakeFlavours.CHOCOLATE;
		cake1 = new Cake();
		cake1.setCategory(category);
		cake1.setOccasions(occasions);
		cake1.setFlavours(flavours);
		cake2 = new Cake();
		cake2.setCategory(category);
		cake2.setOccasions(occasions);
		cake2.setFlavours(flavours);
		expectedCakes.add(cake1);
		expectedCakes.add(cake2);
	}

	@Test
	void testFindByCategory_ShouldReturnListOfCakesWithMatchingCategory() {

		when(cakeDao.findByCategory(category)).thenReturn(expectedCakes);

		List<Cake> result = cakeDao.findByCategory(category);

		assertEquals(expectedCakes.size(), result.size());
		assertTrue(result.containsAll(expectedCakes));
	}

	@Test
	void testFindByOccasions_ShouldReturnListOfCakesWithMatchingOccasions() {

		when(cakeDao.findByOccasions(occasions)).thenReturn(expectedCakes);

		List<Cake> result = cakeDao.findByOccasions(occasions);

		assertEquals(expectedCakes.size(), result.size());
		assertTrue(result.containsAll(expectedCakes));
	}

	@Test
	void testFindByFlavours_ShouldReturnListOfCakesWithMatchingFlavours() {

		when(cakeDao.findByFlavours(flavours)).thenReturn(expectedCakes);

		List<Cake> result = cakeDao.findByFlavours(flavours);

		assertEquals(expectedCakes.size(), result.size());
		assertTrue(result.containsAll(expectedCakes));
	}

}
