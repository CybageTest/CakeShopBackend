package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.AddOns;
import com.littlejoys.service.AddOnsService;

@RestController
@RequestMapping("/api/v1/addons")
public class AddOnsController {
	@Autowired
	private AddOnsService addOnsService;

	@PostMapping("/")
	public ResponseEntity<AddOns> addAddOns(@RequestBody AddOns addOns) {
		return new ResponseEntity<AddOns>(addOnsService.addAddOns(addOns), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddOns> findAddOnsById(@PathVariable long id) {
		return new ResponseEntity<AddOns>(addOnsService.findAddOnById(id), HttpStatus.FOUND);
	}

	@PostMapping("/addAddonsList")
	public ResponseEntity<List<AddOns>> addAddonsList(@RequestBody List<AddOns> addOnsList) {
		return new ResponseEntity<List<AddOns>>(addOnsService.addAddonsList(addOnsList), HttpStatus.CREATED);
	}

	@GetMapping("/getAllAddons")
	public ResponseEntity<List<AddOns>> getAllAddOns() {
		return new ResponseEntity<List<AddOns>>(addOnsService.getAllAddOns(), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<AddOns> deleteAddOnsById(@PathVariable long id) throws Exception{
		return new ResponseEntity<AddOns>(addOnsService.deleteAddOnById(id), HttpStatus.OK);
	}

}
