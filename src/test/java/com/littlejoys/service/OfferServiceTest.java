package com.littlejoys.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import com.littlejoys.dao.IOfferDao;
import com.littlejoys.dto.OfferDTO;
import com.littlejoys.entity.Offer;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.exception.ResourceNotFoundException;

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
		offerDTO = new OfferDTO(10, "Test DTO offer", "TCOD10", 10, "Test DTO offer description", null);
		offer = new Offer(10, "Test DTO offer", "TCOD10", 10, "Test DTO offer description", null);
	}

	@Test
	void testAddOffer_UniqueCode_OfferAdded() throws ResourceAlreadyExistException {
		Offer offerToBeSaved = modelMapper.map(offerDTO, Offer.class);
		when(offerDao.findByCode(offerDTO.getCode())).thenReturn(null);
		when(offerDao.save(offerToBeSaved)).thenReturn(offerToBeSaved);

		Offer savedOffer = offerService.addOffer(offerDTO);

		assertEquals(offerToBeSaved, savedOffer);
		verify(offerDao, times(1)).findByCode(offerDTO.getCode());
		verify(offerDao, times(1)).save(offerToBeSaved);
	}

	@Test
	void testAddOffer_DuplicateCode_ExceptionThrown() throws ResourceAlreadyExistException {
		when(offerDao.findByCode(offerDTO.getCode())).thenReturn(offer);

		assertThrows(ResourceAlreadyExistException.class, () -> offerService.addOffer(offerDTO));
	}

	@Test
	void testFindOfferById_ValidId_OfferReturned() {
		long offerId = 10;

		when(offerDao.findById(offerId)).thenReturn(Optional.of(offer));
		System.out.println(offer);
		OfferDTO foundOfferDTO = offerService.findOfferById(offerId);
		OfferDTO expectedOfferDTO = modelMapper.map(offer, OfferDTO.class);

		assertEquals(expectedOfferDTO, foundOfferDTO);
		verify(offerDao, times(1)).findById(offerId);
	}

	@Test
	void testFindOfferById_InvalidId_ExceptionThrown() {
		long invalidOfferId = 987;
		when(offerDao.findById(invalidOfferId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> offerService.findOfferById(invalidOfferId));
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

	@Test
	void testWhenIdNotFoundForDeleting_ShouldThrowException() {
		long id = 9876;
		Throwable thrown = assertThrows(ResourceNotFoundException.class, () -> offerService.deleteOfferById(id));
		assertEquals("Offer(id) does not exist", thrown.getMessage());
	}

	@Test
	void testEditOfferById_ExistingId_ReturnsUpdatedOffer() throws ResourceNotFoundException {
		long id = 10;

		Offer updatedOffer = new Offer();
		updatedOffer.setId(id);
		updatedOffer.setName("Updated offer name");
		updatedOffer.setDescription("Updated offer description");

		Mockito.when(offerDao.findById(id)).thenReturn(Optional.of(offer));
		Mockito.when(modelMapper.map(offerDTO, Offer.class)).thenReturn(updatedOffer);
		Mockito.when(offerDao.save(updatedOffer)).thenReturn(updatedOffer);

		Map<String, Object> result = offerService.editOfferById(id, offerDTO);

		assertNotNull(result);
		assertTrue(result.containsKey("Offer updated"));
		assertEquals(updatedOffer, result.get("Offer updated"));
	}

	@Test
	void testEditOfferById_NonExistingId_ThrowsResourceNotFoundException() {
		long id = 999;
		Mockito.when(offerDao.findById(id)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> offerService.editOfferById(id, offerDTO));
	}

}
