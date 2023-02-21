package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IOfferDao;
import com.littlejoys.dto.OfferDTO;
import com.littlejoys.entity.Offer;

class OfferServiceTest {

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private OfferService offerService;

	@Mock
	private IOfferDao offerDao;

	private OfferDTO offerDTO;
	private Offer offer;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testAddOffer() {
		fail("Not yet implemented");
	}

	@Test
	void testFindOfferById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllOffers() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteOfferById() {
		fail("Not yet implemented");
	}

}
