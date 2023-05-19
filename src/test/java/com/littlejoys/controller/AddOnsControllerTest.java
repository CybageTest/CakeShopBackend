package com.littlejoys.controller;

import static org.junit.jupiter.api.Assertions.*;

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

import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceNotFoundException;
import com.littlejoys.service.AddOnsService;

class AddOnsControllerTest {

	@InjectMocks
	private AddOnsController addOnsController;

	@Mock
	private AddOnsService addOnsService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddAddOns_ShouldReturnCreatedResponse() {
		AddOnsDTO addOnsDTO = new AddOnsDTO();
		AddOns addOns = new AddOns();
		Mockito.when(addOnsService.addAddOns(addOnsDTO)).thenReturn(addOns);
		ResponseEntity<AddOns> expectedResponse = new ResponseEntity<>(addOns, HttpStatus.CREATED);

		ResponseEntity<AddOns> actualResponse = addOnsController.addAddOns(addOnsDTO);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(addOns, actualResponse.getBody());
		Mockito.verify(addOnsService, Mockito.times(1)).addAddOns(addOnsDTO);
	}

	@Test
	void testAddAddonsList_ShouldReturnCreatedResponse() {
		List<AddOnsDTO> addOnsDTOList = new ArrayList<>();
		List<AddOns> addedAddOnsList = new ArrayList<>();
		Mockito.when(addOnsService.addAddonsList(addOnsDTOList)).thenReturn(addedAddOnsList);
		ResponseEntity<List<AddOns>> expectedResponse = new ResponseEntity<>(addedAddOnsList, HttpStatus.CREATED);

		ResponseEntity<List<AddOns>> actualResponse = addOnsController.addAddonsList(addOnsDTOList);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
		assertEquals(addedAddOnsList, actualResponse.getBody());
		Mockito.verify(addOnsService, Mockito.times(1)).addAddonsList(addOnsDTOList);
	}

	@Test
	void testGetAllAddOns_ShouldReturnOkResponse() {
		List<AddOnsDTO> allAddOns = new ArrayList<>();
		Mockito.when(addOnsService.getAllAddOns()).thenReturn(allAddOns);
		ResponseEntity<List<AddOnsDTO>> expectedResponse = new ResponseEntity<>(allAddOns, HttpStatus.OK);

		ResponseEntity<List<AddOnsDTO>> actualResponse = addOnsController.getAllAddOns();

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(allAddOns, actualResponse.getBody());
		Mockito.verify(addOnsService, Mockito.times(1)).getAllAddOns();
	}

	@Test
	void testFindAddOnsById_ShouldReturnOkResponse() {
		long id = 1L;
		AddOnsDTO addOnsDTO = new AddOnsDTO();
		Mockito.when(addOnsService.findAddOnById(id)).thenReturn(addOnsDTO);
		ResponseEntity<AddOnsDTO> expectedResponse = new ResponseEntity<>(addOnsDTO, HttpStatus.OK);

		ResponseEntity<AddOnsDTO> actualResponse = addOnsController.findAddOnsById(id);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(addOnsDTO, actualResponse.getBody());
		Mockito.verify(addOnsService, Mockito.times(1)).findAddOnById(id);
	}

	@Test
	void testDeleteAddOnsById_ShouldReturnOkResponse() throws ResourceNotFoundException {
		long id = 1L;
		AddOnsDTO deletedAddOnsDTO = new AddOnsDTO();
		Mockito.when(addOnsService.deleteAddOnById(id)).thenReturn(deletedAddOnsDTO);
		ResponseEntity<AddOnsDTO> expectedResponse = new ResponseEntity<>(deletedAddOnsDTO, HttpStatus.OK);

		ResponseEntity<AddOnsDTO> actualResponse = addOnsController.deleteAddOnsById(id);

		assertEquals(expectedResponse, actualResponse);
		assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
		assertEquals(deletedAddOnsDTO, actualResponse.getBody());
		Mockito.verify(addOnsService, Mockito.times(1)).deleteAddOnById(id);
	}

}
