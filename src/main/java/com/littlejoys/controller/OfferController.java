package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.Offer;
import com.littlejoys.service.OfferService;

@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

	@Autowired
	private OfferService offerService;

	@PostMapping("/")
	public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
		return new ResponseEntity<Offer>(offerService.addOffer(offer), HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Offer> findOfferById(@PathVariable long id){
		return new ResponseEntity<Offer>(offerService.findOfferById(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllOffers")
	public ResponseEntity<List<Offer>> getAllOffers(){
		return new ResponseEntity<List<Offer>>(offerService.getAllOffers(), HttpStatus.FOUND);
	}

}
