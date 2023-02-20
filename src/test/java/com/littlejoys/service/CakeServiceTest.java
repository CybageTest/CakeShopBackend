package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
	void testGetAllOccasions() {
//		assertAll(() -> assertEquals("BIRTHDAY", CakeOccasions.BIRTHDAY),
//				() -> assertEquals("WEDDING", CakeOccasions.WEDDING),
//				() -> assertEquals("ANNIVERSARIES", CakeOccasions.ANNIVERSARIES),
//				() -> assertEquals("BABY_SHOWER", CakeOccasions.BABY_SHOWER),
//				() -> assertEquals("VALENTINE_SPECIALS", CakeOccasions.VALENTINE_SPECIALS),
//				() -> assertEquals("CHRISTMAS", CakeOccasions.CHRISTMAS),
//				() -> assertEquals("SPECIAL_DAYS", CakeOccasions.SPECIAL_DAYS));
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
