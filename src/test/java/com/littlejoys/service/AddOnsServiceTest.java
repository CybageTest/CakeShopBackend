package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;

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
		fail("Not yet implemented");
	}

	@Test
	void testAddAddonsList() {
		fail("Not yet implemented");
	}

	@Test
	void testFindAddOnById() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteAddOnById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllAddOns() {
		fail("Not yet implemented");
	}

}
