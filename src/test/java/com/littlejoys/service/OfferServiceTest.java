package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.littlejoys.dao.IOfferDao;
import com.littlejoys.dto.OfferDTO;
import com.littlejoys.entity.Offer;
import com.littlejoys.exception.ResourceAlreadyExistException;

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
		MockitoAnnotations.openMocks(this);
		offerDTO = new OfferDTO(10, "Test DTO offer", "TDO", 10, "Test DTO offer description", null);
		offer = new Offer(10, "Test DTO offer", "TDO", 10, "Test DTO offer description", null);
	}

	@Test
	void testAddOffer() {
		when(modelMapper.map(any(OfferDTO.class), any())).thenReturn(offer);
		when(offerDao.save(any(Offer.class))).thenReturn(offer);
		Offer result = null;
		try {
			result = offerService.addOffer(offerDTO);
		} catch (ResourceAlreadyExistException e) {
			e.printStackTrace();
		}
		assertEquals(offer, result);
	}

	@Test
	void testFindOfferById() {
		long id = 10;
		when(offerDao.findById(id)).thenReturn(Optional.of(offer));
		assertEquals(id, offer.getId());
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
