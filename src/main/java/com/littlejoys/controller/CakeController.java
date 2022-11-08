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

import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<Cake> findCakeById(@PathVariable long id){
		return new ResponseEntity<Cake>(cakeService.findCakeById(id), HttpStatus.FOUND);
	}
	
	@GetMapping("/byCategory/{category}")
	public ResponseEntity<List<Cake>> findAllCakesByCategory(@PathVariable CakeCategory category){
		return new ResponseEntity<List<Cake>>(cakeService.findCakeByCategory(category), HttpStatus.FOUND);
	}
	
	@GetMapping("/byOccasions/{occasions}")
	public ResponseEntity<List<Cake>> findAllCakesByOccassions(@PathVariable CakeOccasions occasions){
		return new ResponseEntity<List<Cake>>(cakeService.findCakeByOccasions(occasions), HttpStatus.FOUND);
	}
	
	@GetMapping("/byFlavours/{flavours}")
	public ResponseEntity<List<Cake>> findAllCakesByFlavours(@PathVariable CakeFlavours flavours){
		return new ResponseEntity<List<Cake>>(cakeService.findCakeByFlavours(flavours), HttpStatus.FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cake> deleteCakeById(@PathVariable long id) throws Exception{
		return new ResponseEntity<Cake>(cakeService.deleteCakeById(id), HttpStatus.OK);
	}
	
	@PutMapping("/editCake/{id}")
	public ResponseEntity<Cake> editCakeById(@PathVariable long id, @RequestBody Cake cake) throws Exception{
		return new ResponseEntity<Cake>(cakeService.editCakeById(id, cake), HttpStatus.OK);
	}
	

}
