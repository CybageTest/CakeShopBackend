package com.littlejoys.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.littlejoys.dto.CakeDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.service.CakeService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/cake")
public class CakeController {

	@Autowired
	private CakeService cakeService;

	@PostMapping("/")
	public ResponseEntity<?> addCake(@RequestBody CakeDTO cakeDto) {
		return new ResponseEntity<>(cakeService.addCake(cakeDto), HttpStatus.CREATED);
	}

	@PostMapping("/addCakesList")
	public ResponseEntity<List<Cake>> addCakesList(@RequestBody List<Cake> cakeList) {
		return new ResponseEntity<>(cakeService.addCakeList(cakeList), HttpStatus.CREATED);
	}

	@GetMapping("/allcakes")
	public ResponseEntity<List<CakeDTO>> getAllCakes() {
		return new ResponseEntity<>(cakeService.getAllCakes(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CakeDTO> findCakeById(@PathVariable long id) {
		return new ResponseEntity<>(cakeService.findCakeById(id), HttpStatus.OK);
	}

	@GetMapping("/byCategory/{category}")
	public ResponseEntity<List<Cake>> findAllCakesByCategory(@PathVariable CakeCategory category) {
		return new ResponseEntity<>(cakeService.findCakeByCategory(category), HttpStatus.OK);
	}

	@GetMapping("/byOccasions/{occasions}")
	public ResponseEntity<List<Cake>> findAllCakesByOccassions(@PathVariable CakeOccasions occasions) {
		return new ResponseEntity<>(cakeService.findCakeByOccasions(occasions), HttpStatus.OK);
	}

	@GetMapping("/byFlavours/{flavours}")
	public ResponseEntity<List<Cake>> findAllCakesByFlavours(@PathVariable CakeFlavours flavours) {
		return new ResponseEntity<>(cakeService.findCakeByFlavours(flavours), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCakeById(@PathVariable long id) throws Exception {
		return new ResponseEntity<>(cakeService.deleteCakeById(id), HttpStatus.OK);
	}

	@PutMapping("/editCake/{id}")
	public ResponseEntity<Cake> editCakeById(@PathVariable long id, @RequestBody Cake cake) throws Exception {
		return new ResponseEntity<>(cakeService.editCakeById(id, cake), HttpStatus.OK);
	}

	@GetMapping("/menu-cakes")
	public List<CakeOccasions> getAllOccasions() {
		return Arrays.asList(cakeService.getAllOccasions());
	}

	@GetMapping("/cake-flavours")
	public CakeFlavours[] getAllFlavours() {
		return cakeService.getAllFlavours();
	}

	@GetMapping("/cake-categories")
	public CakeCategory[] getAllCakeCategories() {
		return cakeService.getAllCategories();
	}

}
