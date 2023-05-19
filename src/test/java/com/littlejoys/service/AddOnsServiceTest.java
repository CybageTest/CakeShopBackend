package com.littlejoys.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceCannotBeNullException;
import com.littlejoys.exception.ResourceNotFoundException;

class AddOnsServiceTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AddOnsService addOnsService;

	@Mock
	private IAddOnsDao addOnsDao;

	private AddOnsDTO addOnsDTO;
	private AddOns addOns;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		addOnsDTO = new AddOnsDTO(1234, "DTO addon", 50, "Test Addon DTO description", null);
		addOns = new AddOns(1234, "Entity addon", 50, "Test Addon Entity description", null);
	}

	@Test
	void testAddAddOns() {
		when(modelMapper.map(any(AddOnsDTO.class), any())).thenReturn(addOns);
		when(addOnsDao.save(any(AddOns.class))).thenReturn(addOns);
		AddOns result = addOnsService.addAddOns(addOnsDTO);
		assertEquals(addOns, result);
	}

	@Test
	void whenAddOnWithInvalidData_thenThrowException() {
		addOnsDTO.setName(null);
		when(modelMapper.map(any(AddOnsDTO.class), any())).thenReturn(addOns);
		Throwable thrown = assertThrows(ResourceCannotBeNullException.class, () -> addOnsService.addAddOns(addOnsDTO));
		assertEquals("AddOn name cannot be empty", thrown.getMessage());
	}

	@Test
	void testAddAddonsList() {
		AddOnsDTO addOnsDTO1 = new AddOnsDTO(1234, "Test addList 1", 50, "Test Addon DTO description", null);
		AddOnsDTO addOnsDTO2 = new AddOnsDTO(1234, "Test addList 2", 50, "Test Addon Entity description", null);
		List<AddOnsDTO> addOnDTOList = Arrays.asList(addOnsDTO1, addOnsDTO2);

		List<AddOns> addOnList = addOnDTOList.stream().map(addOnDto -> modelMapper.map(addOnDto, AddOns.class))
				.collect(Collectors.toList());

		when(addOnsDao.saveAll(addOnList)).thenReturn(addOnList);

		List<AddOns> expectedAddOnsList = addOnsService.addAddonsList(addOnDTOList);

		assertEquals(expectedAddOnsList.size(), addOnList.size());
		assertEquals(expectedAddOnsList, addOnList);
	}

	@Test
	void testFindAddOnById_ValidId_AddOnReturned() {
		long addOnId = 12356;

		when(addOnsDao.findById(addOnId)).thenReturn(Optional.of(addOns));
		AddOnsDTO foundAddOnDTO = addOnsService.findAddOnById(addOnId);
		AddOnsDTO expectedAddOnDTO = modelMapper.map(addOns, AddOnsDTO.class);

		assertEquals(expectedAddOnDTO, foundAddOnDTO);
		verify(addOnsDao, times(1)).findById(addOnId);
	}

	@Test
	void testWhenIdNotFound_ShouldThrowException() {
		long id = 1236;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> addOnsService.findAddOnById(id));
		assertEquals("Addon(id) does not exist", thrown.getMessage());
	}

	@Test
	void testDeleteAddOnById() {
		long id = 12356;

		when(addOnsDao.findById(id)).thenReturn(Optional.of(addOns));
		AddOnsDTO mappedAddOnDTO = modelMapper.map(addOns, AddOnsDTO.class);
		AddOnsDTO expectedAddOnDTO = addOnsService.deleteAddOnById(id);

		assertEquals(expectedAddOnDTO, mappedAddOnDTO);
	}

	@Test
	void testWhenIdNotFoundForDeleting_ShouldThrowException() {
		long id = 1236;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> addOnsService.deleteAddOnById(id));
		assertEquals("AddOns(id) does not exist", thrown.getMessage());
	}

	@Test
	void testGetAllAddOns() {
		AddOns addOns1 = new AddOns(1234, "Test addList 1", 50, "Test Addon DTO description", null);
		AddOns addOns2 = new AddOns(1234, "Test addList 2", 50, "Test Addon Entity description", null);
		List<AddOns> addOnsList = Arrays.asList(addOns1, addOns2);

		when(addOnsDao.findAll()).thenReturn(addOnsList);

		List<AddOnsDTO> addOnsDTOList = addOnsService.getAllAddOns();

		List<AddOnsDTO> expectedAddOnsList = addOnsList.stream().map(addOn -> modelMapper.map(addOn, AddOnsDTO.class))
				.collect(Collectors.toList());

		assertEquals(expectedAddOnsList, addOnsDTOList);
	}

	@Test
	void testEditAddOnById_ExistingId_ReturnsUpdatedAddOn() throws ResourceNotFoundException {
		long id = 1234;

		AddOns updatedAddOn = new AddOns();
		updatedAddOn.setId(id);
		updatedAddOn.setName("Updated AddOn name");
		updatedAddOn.setDescription("Updated addon description");

		Mockito.when(addOnsDao.findById(id)).thenReturn(Optional.of(addOns));
		Mockito.when(modelMapper.map(addOnsDTO, AddOns.class)).thenReturn(updatedAddOn);
		Mockito.when(addOnsDao.save(updatedAddOn)).thenReturn(updatedAddOn);

		Map<String, Object> result = addOnsService.editAddOnById(id, addOnsDTO);

		assertNotNull(result);
		assertTrue(result.containsKey("AddOn updated"));
		assertEquals(updatedAddOn, result.get("AddOn updated"));
	}

	@Test
	void testEditAddOnById_NonExistingId_ThrowsResourceNotFoundException() {
		long id = 999;

		Mockito.when(addOnsDao.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> addOnsService.editAddOnById(id, addOnsDTO));
	}

}
