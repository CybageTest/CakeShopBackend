package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.dto.CakeDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.exception.ResourceCannotBeNullException;

class CakeServiceTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private CakeService cakeService;

	@Mock
	private ICakeDao cakeDao;

	private Cake cake;
	private CakeDTO cakeDTO;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		cakeDTO = new CakeDTO(12356, "lava cake", 123.456, "swwet cake", 1, CakeFlavours.CHOCOLATE, CakeCategory.EGG,
				CakeOccasions.BIRTHDAY, null);

		cake = new Cake(12356, "lava cake", 123.456, "swwet cake", 1, CakeFlavours.CHOCOLATE, CakeCategory.EGG,
				CakeOccasions.BIRTHDAY, null);

	}

	@Test
	void testAddCake() {
		when(modelMapper.map(any(CakeDTO.class), any())).thenReturn(cake);
		when(cakeDao.save(any(Cake.class))).thenReturn(cake);
		Cake result = cakeService.addCake(cakeDTO);
		assertEquals(cake, result);
	}

	@Test
	void whenAddCakeWithInvalidData_thenThrowException() {
		cakeDTO.setCakeName(null);
		when(modelMapper.map(any(CakeDTO.class), any())).thenReturn(cake);
		Throwable thrown = assertThrows(ResourceCannotBeNullException.class, () -> cakeService.addCake(cakeDTO));
		assertEquals("Cake name cannot be empty", thrown.getMessage());
	}

	@Test
	void testGetAllOccasions() {
		assertAll(() -> assertEquals(CakeOccasions.BIRTHDAY, CakeOccasions.valueOf("BIRTHDAY")),
				() -> assertEquals(CakeOccasions.WEDDING, CakeOccasions.valueOf("WEDDING")),
				() -> assertEquals(CakeOccasions.ANNIVERSARIES, CakeOccasions.valueOf("ANNIVERSARIES")),
				() -> assertEquals(CakeOccasions.BABY_SHOWER, CakeOccasions.valueOf("BABY_SHOWER")),
				() -> assertEquals(CakeOccasions.VALENTINE_SPECIALS, CakeOccasions.valueOf("VALENTINE_SPECIALS")),
				() -> assertEquals(CakeOccasions.CHRISTMAS, CakeOccasions.valueOf("CHRISTMAS")),
				() -> assertEquals(CakeOccasions.SPECIAL_DAYS, CakeOccasions.valueOf("SPECIAL_DAYS")));
	}

	@Test
	void testGetAllCategories() {
//		assertAll(() -> assertEquals("EGG", CakeCategory.EGG), () -> assertEquals("EGGLESS", CakeCategory.EGGLESS));
	}

	@Test
	void testGetAllFlavours() {
		fail("Not yet implemented");
	}

	@Test
	void testAddCakeList() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllCakes() {
		fail("Not yet implemented");
	}

	@Test
	void testFindCakeById() {
//		CakeDTO cakeDTO = cakeService.findCakeById(1L);
//		assertNotNull(cakeDTO);
//		assertEquals(1L, cakeDTO.getId());
//		when(cakeDao.findById(anyLong())).thenReturn(cakeToSave);
//		CakeDTO cakeDTO = cakeService.findCakeById(1L);
//		assertNotNull(cakeDTO);
//		assertEquals(1L, cakeDTO.getId());
		fail("Not yet implemented");
	}

	@Test
	void testFindCakeByCategory() {
		fail("Not yet implemented");
	}

	@Test
	void testFindCakeByOccasions() {
		fail("Not yet implemented");
	}

	@Test
	void testFindCakeByFlavours() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteCakeById() {
		fail("Not yet implemented");
	}

	@Test
	void testEditCakeById() {
		fail("Not yet implemented");
	}

}
