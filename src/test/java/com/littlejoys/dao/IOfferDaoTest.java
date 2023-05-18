package com.littlejoys.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.Offer;

class IOfferDaoTest {

	@Mock
	private IOfferDao offerDao;

	private Offer offer;
	private String code = "XYZ987";

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		offer = new Offer(123, code, "Test offer", 10, "Test Offer description", null);
	}

	@Test
	void testFindByCode_ExistingCode_ShouldReturnOffer() {

		when(offerDao.findByCode(code)).thenReturn(offer);

		Offer result = offerDao.findByCode(code);

		assertNotNull(result);
		assertEquals(offer.getId(), result.getId());
		assertEquals(offer.getCode(), result.getCode());
	}

}
