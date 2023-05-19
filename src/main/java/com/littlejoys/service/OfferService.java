package com.littlejoys.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
		Optional<Offer> offerOptional = offerDao.findById(id);
		if (offerOptional.isPresent()) {
			Offer offerToFind = offerOptional.get();
			logger.info("Found Offer: " + offerToFind + " for ID: " + id);
			return modelMapper.map(offerToFind, OfferDTO.class);
		} else {
			throw new ResourceNotFoundException("Offer(id) does not exist");
		}
	}

	public List<OfferDTO> getAllOffers() {
		List<Offer> offersList = offerDao.findAll();
		return offersList.stream().map(offer -> modelMapper.map(offer, OfferDTO.class)).collect(Collectors.toList());
	}

	public Map<String, Object> editOfferById(long id, OfferDTO offerDTO) {
		Optional<Offer> offerToFind = offerDao.findById(id);
		if (offerToFind.isEmpty()) {
			throw new ResourceNotFoundException("Offer with " + id + " does not exist");
		} else {
			offerDTO.setId(id);
			Offer offer = modelMapper.map(offerDTO, Offer.class);
			offer = offerDao.save(offer);
			logger.info("Offer: " + offer + " for Id: " + id + " updated.");
			return Collections.singletonMap("Offer updated", offer);
		}
	}

	public OfferDTO deleteOfferById(long id) throws ResourceNotFoundException {
		Optional<Offer> offerOptional = offerDao.findById(id);
		if (offerOptional.isPresent()) {
			Offer offerToDelete = offerOptional.get();
			offerDao.deleteById(id);
			logger.info("Deleted offer: " + offerToDelete + " for ID: " + id);
			return modelMapper.map(offerToDelete, OfferDTO.class);
		} else {
			throw new ResourceNotFoundException("Offer(id) does not exist");
		}
	}

}
