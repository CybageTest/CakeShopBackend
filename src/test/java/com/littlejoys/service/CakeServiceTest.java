package com.littlejoys.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.dto.CakeDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.exception.ResourceCannotBeNullException;
import com.littlejoys.exception.ResourceNotFoundException;

class CakeServiceTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private CakeService cakeService;

	@Mock
	private ICakeDao cakeDao;

	private Cake cake;
	private CakeDTO cakeDTO;
	private CakeCategory category = CakeCategory.EGG;
	private CakeOccasions occasion = CakeOccasions.BIRTHDAY;
	private CakeFlavours flavour = CakeFlavours.CHOCOLATE;
	private List<Cake> expectedCakes = new ArrayList<>();

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		cakeDTO = new CakeDTO(12356, "lava cake", 123.456, "swwet cake", 1, flavour, category, occasion, null);
		cake = new Cake(12356, "lava cake", 123.456, "swwet cake", 1, flavour, category, occasion, null);
		expectedCakes = Arrays.asList(
				new Cake(12500, "TestCakeName 1", 1234, "TestDescription 1", 1, flavour, category, occasion, null),
				new Cake(12501, "TestCakeName 2", 1234, "TestDescription 1", 1, flavour, category, occasion, null));
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
	void testGetAllOccasions_ReturnsAllOccasions() {
		CakeOccasions[] expectedOccasions = CakeOccasions.values();
		CakeOccasions[] actualOccasions = cakeService.getAllOccasions();

		assertNotNull(actualOccasions);
		assertArrayEquals(expectedOccasions, actualOccasions);
	}

	@Test
	void testGetAllCategories_ReturnsAllCategories() {
		CakeCategory[] expectedCategories = CakeCategory.values();
		CakeCategory[] actualCategories = cakeService.getAllCategories();

		assertNotNull(actualCategories);
		assertArrayEquals(expectedCategories, actualCategories);
	}

	@Test
	void testGetAllFlavours_ReturnsAllFlavours() {
		CakeFlavours[] expectedFlavours = CakeFlavours.values();
		CakeFlavours[] actualFlavours = cakeService.getAllFlavours();

		assertNotNull(actualFlavours);
		assertArrayEquals(expectedFlavours, actualFlavours);
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
	void testFindCakeById_ValidId_CakeReturned() {
		long cakeId = 12356;

		when(cakeDao.findById(cakeId)).thenReturn(Optional.of(cake));
		CakeDTO foundCakeDTO = cakeService.findCakeById(cakeId);
		CakeDTO expectedCakeDTO = modelMapper.map(cake, CakeDTO.class);

		assertEquals(expectedCakeDTO, foundCakeDTO);
		verify(cakeDao, times(1)).findById(cakeId);
	}

	@Test
	void testWhenIdNotFound_ShouldThrowException() {
		long id = 1236;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> cakeService.findCakeById(id));
		assertEquals("Cake(id) does not exist", thrown.getMessage());
	}

	@Test
	void testDeleteCakeById() {
		long id = 12356;

		when(cakeDao.findById(id)).thenReturn(Optional.of(cake));
		CakeDTO mappedCakeDTO = modelMapper.map(cake, CakeDTO.class);
		CakeDTO expectedCakeDTO = cakeService.deleteCakeById(id);

		assertEquals(expectedCakeDTO, mappedCakeDTO);
	}

	@Test
	void testWhenIdNotFoundForDeleting_ShouldThrowException() {
		long id = 9876;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> cakeService.deleteCakeById(id));
		assertEquals("Cake(id) does not exist", thrown.getMessage());
	}

	@Test
	void testFindCakeByCategory_ReturnsCakesByCategory() {
		when(cakeDao.findByCategory(category)).thenReturn(expectedCakes);

		List<Cake> actualCakes = cakeService.findCakeByCategory(category);

		assertNotNull(actualCakes);
		assertEquals(expectedCakes.size(), actualCakes.size());
		assertEquals(expectedCakes, actualCakes);
	}

	@Test
	void testFindCakeByOccasions_ReturnsCakesByOccasions() {
		when(cakeDao.findByOccasions(occasion)).thenReturn(expectedCakes);

		List<Cake> actualCakes = cakeService.findCakeByOccasions(occasion);

		assertNotNull(actualCakes);
		assertEquals(expectedCakes.size(), actualCakes.size());
		assertEquals(expectedCakes, actualCakes);
	}

	@Test
	void testFindCakeByFlavours_ReturnsCakesByFlavours() {
		when(cakeDao.findByFlavours(flavour)).thenReturn(expectedCakes);

		List<Cake> actualCakes = cakeService.findCakeByFlavours(flavour);

		assertNotNull(actualCakes);
		assertEquals(expectedCakes.size(), actualCakes.size());
		assertEquals(expectedCakes, actualCakes);
	}

	@Test
	void testEditCakeById_ExistingId_ReturnsUpdatedCake() throws ResourceNotFoundException {
		long id = 12356;

		Cake updatedCake = new Cake();
		updatedCake.setId(id);
		updatedCake.setCakeName("Updated Cake name");
		updatedCake.setDescription("Updated cake description");

		Mockito.when(cakeDao.findById(id)).thenReturn(Optional.of(cake));
		Mockito.when(modelMapper.map(cakeDTO, Cake.class)).thenReturn(updatedCake);
		Mockito.when(cakeDao.save(updatedCake)).thenReturn(updatedCake);

		Map<String, Cake> result = cakeService.editCakeById(id, cakeDTO);

		assertNotNull(result);
		assertTrue(result.containsKey("Cake updated"));
		assertEquals(updatedCake, result.get("Cake updated"));
	}

	@Test
	void testEditCakeById_NonExistingId_ThrowsResourceNotFoundException() {
		long id = 999;
		Mockito.when(cakeDao.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> cakeService.editCakeById(id, cakeDTO));
	}

}
