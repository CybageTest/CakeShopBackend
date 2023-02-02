package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

		System.out.println(cake + " " + cakeDTO);

	}

	@Test
	void testAddCake() {
//		when(modelMapper.map(any(CakeDTO.class), any())).thenReturn(cake);
//		when(cakeDao.save(any(Cake.class))).thenReturn(cake);
//		CakeDTO cakeResult = cakeService.addCake(cakeDTO);

		// Configure mock to return cakeDTO when map is called
		// when(modelMapper.map(cake, CakeDTO.class)).thenReturn(cakeDTO);
		System.out.println("BEdire: "+cakeDTO);
		when(modelMapper.map(any(CakeDTO.class), any())).thenReturn(cake);

		// Configure mock to return cake when save is called
		// when(cakeRepository.save(cake)).thenReturn(cake);
		when(cakeDao.save(any(Cake.class))).thenReturn(cake);

		System.out.println("CAKEDTO: " + cakeDTO);
		CakeDTO result = cakeService.addCake(cakeDTO);
		System.out.println("RESULT: " + result);
		// Verify that the save method is called once on the repository
		verify(cakeDao, times(1)).save(cake);

		// Verify that the map method is called once with the correct parameters
		verify(modelMapper, times(1)).map(cake, CakeDTO.class);

		// Verify that the result is the same as the cakeDTO that was passed in
		System.out.println("REs: " + result + "CakeDTO: " + cakeDTO);
		assertEquals(result, cakeDTO);

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
