package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.dto.OfferDTO;
import com.littlejoys.service.OfferService;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

	@Autowired
	private OfferService offerService;

	@PostMapping("/")
	public ResponseEntity<OfferDTO> addOffer(@RequestBody OfferDTO offerDTO) {
		return new ResponseEntity<>(offerService.addOffer(offerDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OfferDTO> findOfferById(@PathVariable long id) {
		return new ResponseEntity<>(offerService.findOfferById(id), HttpStatus.OK);
	}

	@GetMapping("/getAllOffers")
	public ResponseEntity<List<OfferDTO>> getAllOffers() {
		return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<OfferDTO> deleteOfferById(@PathVariable long id) throws Exception {
		return new ResponseEntity<>(offerService.deleteOfferById(id), HttpStatus.OK);
	}

	@PutMapping("/editOffer/{id}")
	public ResponseEntity<?> editOfferById(@PathVariable long id, @RequestBody OfferDTO offerDTO) throws Exception {
		return new ResponseEntity<>(offerService.editOfferById(id, offerDTO), HttpStatus.OK);
	}

}
