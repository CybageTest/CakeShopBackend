package com.littlejoys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

}
