package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.any;
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

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceCannotBeNullException;

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
	void testFindAddOnById() {
		long id = 1234;
		when(addOnsDao.findById(id)).thenReturn(Optional.of(addOns));
		assertEquals(id, addOns.getId());
	}

	@Test
	void testDeleteAddOnById() {
		fail("Not yet implemented");
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

}
