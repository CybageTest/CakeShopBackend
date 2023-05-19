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

import com.littlejoys.dto.OfferDTO;
import com.littlejoys.entity.Offer;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.service.OfferService;

class OfferControllerTest {

	@Mock
	private OfferService offerService;

	@InjectMocks
	private OfferController offerController;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddOffer() throws ResourceAlreadyExistException {
		OfferDTO offerDTO = new OfferDTO();
		Offer expectedOffer = new Offer();
		Mockito.when(offerService.addOffer(offerDTO)).thenReturn(expectedOffer);

		ResponseEntity<Offer> response = offerController.addOffer(offerDTO);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(expectedOffer, response.getBody());
		Mockito.verify(offerService, Mockito.times(1)).addOffer(offerDTO);
	}

	@Test
	void testFindOfferById() {
		long offerId = 1L;
		OfferDTO expectedOfferDTO = new OfferDTO();
		Mockito.when(offerService.findOfferById(offerId)).thenReturn(expectedOfferDTO);

		ResponseEntity<OfferDTO> response = offerController.findOfferById(offerId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOfferDTO, response.getBody());
		Mockito.verify(offerService, Mockito.times(1)).findOfferById(offerId);
	}

	@Test
	void testGetAllOffers() {
		List<OfferDTO> expectedOffers = new ArrayList<>();
		Mockito.when(offerService.getAllOffers()).thenReturn(expectedOffers);

		ResponseEntity<List<OfferDTO>> response = offerController.getAllOffers();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOffers, response.getBody());
		Mockito.verify(offerService, Mockito.times(1)).getAllOffers();
	}

	@Test
	void testDeleteOfferById() throws Exception {
		long offerId = 1L;
		OfferDTO expectedOfferDTO = new OfferDTO();
		Mockito.when(offerService.deleteOfferById(offerId)).thenReturn(expectedOfferDTO);

		ResponseEntity<OfferDTO> response = offerController.deleteOfferById(offerId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedOfferDTO, response.getBody());
		Mockito.verify(offerService, Mockito.times(1)).deleteOfferById(offerId);
	}

}
