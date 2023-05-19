package com.littlejoys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.littlejoys.dto.CakeDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.exception.ResourceNotFoundException;
import com.littlejoys.service.CakeService;

class CakeControllerTest {

	@Mock
	private CakeService cakeService;

	@InjectMocks
	private CakeController cakeController;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddCake_SuccessfulAddition_ShouldReturnCreatedResponseWithCake() {
		CakeDTO cakeDto = new CakeDTO();
		Cake createdCake = new Cake();
		Mockito.when(cakeService.addCake(cakeDto)).thenReturn(createdCake);
		ResponseEntity<Cake> expectedResponse = new ResponseEntity<>(createdCake, HttpStatus.CREATED);

		ResponseEntity<Cake> actualResponse = cakeController.addCake(cakeDto);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(createdCake, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).addCake(cakeDto);
	}

	@Test
	void testAddCakesList_SuccessfulAddition_ShouldReturnCreatedResponseWithCakesList() {
		List<CakeDTO> cakeDTOList = new ArrayList<>();
		List<Cake> createdCakesList = new ArrayList<>();
		Mockito.when(cakeService.addCakeList(cakeDTOList)).thenReturn(createdCakesList);
		ResponseEntity<List<Cake>> expectedResponse = new ResponseEntity<>(createdCakesList, HttpStatus.CREATED);

		ResponseEntity<List<Cake>> actualResponse = cakeController.addCakesList(cakeDTOList);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(createdCakesList, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).addCakeList(cakeDTOList);
	}

	@Test
	void testGetAllCakes_SuccessfulRetrieval_ShouldReturnOkResponseWithCakesList() {
		List<CakeDTO> allCakesList = new ArrayList<>();
		Mockito.when(cakeService.getAllCakes()).thenReturn(allCakesList);
		ResponseEntity<List<CakeDTO>> expectedResponse = new ResponseEntity<>(allCakesList, HttpStatus.OK);

		ResponseEntity<List<CakeDTO>> actualResponse = cakeController.getAllCakes();

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(allCakesList, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).getAllCakes();
	}

	@Test
	void testFindCakeById_ExistingCakeId_ShouldReturnOkResponseWithCake() {
		long id = 1L;
		CakeDTO cakeDto = new CakeDTO();
		Mockito.when(cakeService.findCakeById(id)).thenReturn(cakeDto);
		ResponseEntity<CakeDTO> expectedResponse = new ResponseEntity<>(cakeDto, HttpStatus.OK);

		ResponseEntity<CakeDTO> actualResponse = cakeController.findCakeById(id);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(cakeDto, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).findCakeById(id);
	}

	@Test
	void testFindAllCakesByCategory_ValidCategory_ShouldReturnOkResponseWithCakeList() {
		CakeCategory category = CakeCategory.EGG;
		List<Cake> cakeList = new ArrayList<>();
		Mockito.when(cakeService.findCakeByCategory(category)).thenReturn(cakeList);
		ResponseEntity<List<Cake>> expectedResponse = new ResponseEntity<>(cakeList, HttpStatus.OK);

		ResponseEntity<List<Cake>> actualResponse = cakeController.findAllCakesByCategory(category);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(cakeList, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).findCakeByCategory(category);
	}

	@Test
	void testFindAllCakesByOccasions_ValidOccasions_ShouldReturnOkResponseWithCakeList() {
		CakeOccasions occasions = CakeOccasions.WEDDING;
		List<Cake> cakeList = new ArrayList<>();
		Mockito.when(cakeService.findCakeByOccasions(occasions)).thenReturn(cakeList);
		ResponseEntity<List<Cake>> expectedResponse = new ResponseEntity<>(cakeList, HttpStatus.OK);

		ResponseEntity<List<Cake>> actualResponse = cakeController.findAllCakesByOccassions(occasions);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(cakeList, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).findCakeByOccasions(occasions);
	}

	@Test
	void testFindAllCakesByFlavours_ValidFlavours_ShouldReturnOkResponseWithCakeList() {
		CakeFlavours flavours = CakeFlavours.CHOCOLATE;
		List<Cake> cakeList = new ArrayList<>();
		Mockito.when(cakeService.findCakeByFlavours(flavours)).thenReturn(cakeList);
		ResponseEntity<List<Cake>> expectedResponse = new ResponseEntity<>(cakeList, HttpStatus.OK);

		ResponseEntity<List<Cake>> actualResponse = cakeController.findAllCakesByFlavours(flavours);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(cakeList, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).findCakeByFlavours(flavours);
	}

	@Test
	void testDeleteCakeById_ValidId_ShouldReturnOkResponseWithDeletedCake() throws ResourceNotFoundException {
		long id = 1;
		CakeDTO deletedCake = new CakeDTO();
		Mockito.when(cakeService.deleteCakeById(id)).thenReturn(deletedCake);
		ResponseEntity<CakeDTO> expectedResponse = new ResponseEntity<>(deletedCake, HttpStatus.OK);

		ResponseEntity<CakeDTO> actualResponse = cakeController.deleteCakeById(id);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(deletedCake, actualResponse.getBody());
		Mockito.verify(cakeService, Mockito.times(1)).deleteCakeById(id);
	}

	@Test
	void testGetAllOccasions() {
		CakeOccasions[] expectedOccasions = CakeOccasions.values();
		Mockito.when(cakeService.getAllOccasions()).thenReturn(expectedOccasions);

		List<CakeOccasions> actualOccasions = cakeController.getAllOccasions();

		assertEquals(expectedOccasions.length, actualOccasions.size());
		for (int i = 0; i < expectedOccasions.length; i++) {
			assertEquals(expectedOccasions[i], actualOccasions.get(i));
		}
		Mockito.verify(cakeService, Mockito.times(1)).getAllOccasions();
	}

	@Test
	void testGetAllFlavours() {
		CakeFlavours[] expectedFlavours = CakeFlavours.values();
		Mockito.when(cakeService.getAllFlavours()).thenReturn(expectedFlavours);

		CakeFlavours[] actualFlavours = cakeController.getAllFlavours();

		assertEquals(expectedFlavours, actualFlavours);
		Mockito.verify(cakeService, Mockito.times(1)).getAllFlavours();
	}

	@Test
	void testGetAllCakeCategories() {
		CakeCategory[] expectedCategories = CakeCategory.values();
		Mockito.when(cakeService.getAllCategories()).thenReturn(expectedCategories);

		CakeCategory[] actualCategories = cakeController.getAllCakeCategories();

		assertEquals(expectedCategories, actualCategories);
		Mockito.verify(cakeService, Mockito.times(1)).getAllCategories();
	}

}
