package com.littlejoys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.entity.Cake;
import com.littlejoys.service.CakeService;

@RestController
@RequestMapping("/api/v1/cake")
public class CakeController {

	@Autowired
	private CakeService cakeService;

	@PostMapping("/")
	public ResponseEntity<Cake> addCake(@RequestBody Cake cake) {
		return new ResponseEntity<Cake>(cakeService.addCake(cake), HttpStatus.CREATED);
	}
	
	@PostMapping("/addCakesList")
	public ResponseEntity<List<Cake>> addCakesList(@RequestBody List<Cake> cakeList){
		return new ResponseEntity<List<Cake>>(cakeService.addCakeList(cakeList), HttpStatus.CREATED);
	}
	
	@GetMapping("/allcakes")
	public ResponseEntity<List<Cake>> getAllCakes(){
		return new ResponseEntity<List<Cake>>(cakeService.getAllCakes(), HttpStatus.FOUND);
	}

}
