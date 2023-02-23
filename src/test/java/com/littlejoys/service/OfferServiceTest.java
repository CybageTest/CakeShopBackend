package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		Offer offer1 = new Offer(10, "Test DTO offer 1", "TDO1", 10, "Test DTO offer description 1", null);
		Offer offer2 = new Offer(11, "Test DTO offer 1", "TDO2", 10, "Test DTO offer description 2", null);
		List<Offer> offersList = Arrays.asList(offer1, offer2);

		when(offerDao.findAll()).thenReturn(offersList);

		List<OfferDTO> offerDTOList = offerService.getAllOffers();

		List<OfferDTO> expectedOfferList = offersList.stream().map(offer -> modelMapper.map(offer, OfferDTO.class))
				.collect(Collectors.toList());

		assertEquals(expectedOfferList, offerDTOList);
	}

	@Test
	void testDeleteOfferById() {
		long id = 10;
		when(offerDao.findById(id)).thenReturn(Optional.of(offer));
		OfferDTO mappedOfferDTO = modelMapper.map(offer, OfferDTO.class);
		OfferDTO expectedOfferDTO = offerService.deleteOfferById(id);
		assertEquals(expectedOfferDTO, mappedOfferDTO);
	}

}
