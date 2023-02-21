package com.littlejoys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IOfferDao;
import com.littlejoys.dto.OfferDTO;
import com.littlejoys.entity.Offer;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class OfferService {

	@Autowired
	private IOfferDao offerDao;

	@Autowired
	private ModelMapper modelMapper;

	public static final Logger logger = Logger.getLogger(OfferService.class);

	public Offer addOffer(OfferDTO offerDTO) throws ResourceAlreadyExistException {
		Offer codeToCheck = offerDao.findByCode(offerDTO.getCode());
		if (codeToCheck != null) {
			throw new ResourceAlreadyExistException("Offer code already Exist");
		}
		offerDTO.setCode(offerDTO.getCode().toUpperCase());
		Offer offerToBeSaved = modelMapper.map(offerDTO, Offer.class);
		return offerDao.save(offerToBeSaved);
	}

	public OfferDTO findOfferById(long id) {
		OfferDTO offerDTO = modelMapper.map(offerDao.findById(id), OfferDTO.class);
		if (offerDTO != null) {
			logger.info("Found Offer: " + offerDTO + " for ID: " + id);
			return offerDTO;
		} else {
			throw new ResourceNotFoundException("Offer(id) does not exist");
		}
	}

	public List<OfferDTO> getAllOffers() {
		List<OfferDTO> offerDTOList = new ArrayList<>();
		List<Offer> offersList = offerDao.findAll();
		for (Offer offer : offersList) {
			OfferDTO offerDTO = modelMapper.map(offer, OfferDTO.class);
			offerDTOList.add(offerDTO);
		}
		return offerDTOList;
	}

	public Map<String, Object> editOfferById(long id, OfferDTO offerDTO) {
		if (offerDao.existsById(id)) {
			offerDTO.setId(id);
			Offer offer = modelMapper.map(offerDTO, Offer.class);
			offer = offerDao.save(offer);
			logger.info("Offer updated: " + offer);
			return Collections.singletonMap("Offer updated", offer);
		}
		return Collections.singletonMap("Offer updation failed", 0);
	}

	public OfferDTO deleteOfferById(long id) throws ResourceNotFoundException {
		OfferDTO offerToDelete = findOfferById(id);
		if (offerToDelete != null) {
			offerDao.deleteById(id);
			logger.info("Deleted offer: " + offerToDelete + " for ID: " + id);
			return offerToDelete;
		}
		throw new ResourceNotFoundException("Offer(id) does not exist");
	}

}
