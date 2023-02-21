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

import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.service.AddOnsService;

@RestController
@RequestMapping("/api/v1/addons")
public class AddOnsController {
	@Autowired
	private AddOnsService addOnsService;

	@PostMapping("/")
	public ResponseEntity<?> addAddOns(@RequestBody AddOnsDTO addOnsDTO) {
		return new ResponseEntity<>(addOnsService.addAddOns(addOnsDTO), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AddOnsDTO> findAddOnsById(@PathVariable long id) {
		return new ResponseEntity<>(addOnsService.findAddOnById(id), HttpStatus.OK);
	}

	@PostMapping("/addAddonsList")
	public ResponseEntity<List<AddOns>> addAddonsList(@RequestBody List<AddOnsDTO> addOnsDTOList) {
		return new ResponseEntity<>(addOnsService.addAddonsList(addOnsDTOList), HttpStatus.CREATED);
	}

	@GetMapping("/getAllAddons")
	public ResponseEntity<List<AddOnsDTO>> getAllAddOns() {
		return new ResponseEntity<>(addOnsService.getAllAddOns(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AddOnsDTO> deleteAddOnsById(@PathVariable long id) throws Exception {
		return new ResponseEntity<>(addOnsService.deleteAddOnById(id), HttpStatus.OK);
	}

	@PutMapping("/editAddon/{id}")
	public ResponseEntity<?> editAddOnsById(@PathVariable long id, @RequestBody AddOnsDTO addOnsDTO) throws Exception {
		return new ResponseEntity<>(addOnsService.editAddOnById(id, addOnsDTO), HttpStatus.OK);
	}

}
