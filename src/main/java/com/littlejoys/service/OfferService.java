package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOfferDao;
import com.littlejoys.entity.Offer;


@Service
public class OfferService {

	@Autowired
	private IOfferDao offerDao;
	
	public Offer addOffer(Offer offer) {
		return offerDao.save(offer);
	}
	
	
}
