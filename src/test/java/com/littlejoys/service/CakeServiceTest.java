package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
		assertAll(() -> assertEquals(CakeCategory.EGG, CakeCategory.valueOf("EGG")),
				() -> assertEquals(CakeCategory.EGGLESS, CakeCategory.valueOf("EGGLESS")));
	}

	@Test
	void testGetAllFlavours() {
		assertAll(() -> assertEquals(CakeFlavours.CHOCOLATE, CakeFlavours.valueOf("CHOCOLATE")),
				() -> assertEquals(CakeFlavours.VANILLA, CakeFlavours.valueOf("VANILLA")),
				() -> assertEquals(CakeFlavours.STRAWBERRY, CakeFlavours.valueOf("STRAWBERRY")),
				() -> assertEquals(CakeFlavours.MANGO, CakeFlavours.valueOf("MANGO")),
				() -> assertEquals(CakeFlavours.RASBERRY, CakeFlavours.valueOf("RASBERRY")),
				() -> assertEquals(CakeFlavours.BLUEBERRY, CakeFlavours.valueOf("BLUEBERRY")),
				() -> assertEquals(CakeFlavours.BLACK_FOREST, CakeFlavours.valueOf("BLACK_FOREST")),
				() -> assertEquals(CakeFlavours.BANANA, CakeFlavours.valueOf("BANANA")),
				() -> assertEquals(CakeFlavours.BUTTERSCOTCH, CakeFlavours.valueOf("BUTTERSCOTCH")),
				() -> assertEquals(CakeFlavours.RED_VELVET, CakeFlavours.valueOf("RED_VELVET")),
				() -> assertEquals(CakeFlavours.FRUIT, CakeFlavours.valueOf("FRUIT")),
				() -> assertEquals(CakeFlavours.PINEAPPLE, CakeFlavours.valueOf("PINEAPPLE")),
				() -> assertEquals(CakeFlavours.RASMALAI, CakeFlavours.valueOf("RASMALAI")),
				() -> assertEquals(CakeFlavours.CHEESECAKE, CakeFlavours.valueOf("CHEESECAKE")));
	}

	@Test
	void testAddCakeList() {
		CakeDTO cakeDTO1 = new CakeDTO(12356, "TestCakeName 1", 1234, "TestDescription 1", 1, CakeFlavours.BANANA,
				CakeCategory.EGG, CakeOccasions.BIRTHDAY, null);
		CakeDTO cakeDTO2 = new CakeDTO(12357, "TestCakeName 2", 1234, "TestDescription 2", 1, CakeFlavours.BANANA,
				CakeCategory.EGG, CakeOccasions.BIRTHDAY, null);
		List<CakeDTO> cakeDTOList = Arrays.asList(cakeDTO1, cakeDTO2);

		List<Cake> cakeList = cakeDTOList.stream().map(cakeDto -> modelMapper.map(cakeDto, Cake.class))
				.collect(Collectors.toList());

		when(cakeDao.saveAll(cakeList)).thenReturn(cakeList);

		List<Cake> expectedCakeList = cakeService.addCakeList(cakeDTOList);

		assertEquals(expectedCakeList.size(), cakeList.size());
		assertEquals(expectedCakeList, cakeList);

	}

	@Test
	void testGetAllCakes() {
		Cake cake1 = new Cake(12356, "TestCakeName 1", 1234, "TestDescription 1", 1, CakeFlavours.BANANA,
				CakeCategory.EGG, CakeOccasions.BIRTHDAY, null);
		Cake cake2 = new Cake(12357, "TestCakeName 2", 1234, "TestDescription 2", 1, CakeFlavours.BANANA,
				CakeCategory.EGG, CakeOccasions.BIRTHDAY, null);
		List<Cake> cakeList = Arrays.asList(cake1, cake2);

		when(cakeDao.findAll()).thenReturn(cakeList);

		List<CakeDTO> cakeDTOList = cakeService.getAllCakes();

		List<CakeDTO> expectedCakesList = cakeList.stream().map(cake -> modelMapper.map(cake, CakeDTO.class))
				.collect(Collectors.toList());

		assertEquals(expectedCakesList, cakeDTOList);
	}

	@Test
	void testFindCakeById() {
		long id = 12356;
		when(cakeDao.findById(id)).thenReturn(Optional.of(cake));
		assertEquals(id, cake.getId());
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
