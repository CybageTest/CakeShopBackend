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

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.dto.CakeDTO;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.exception.ResourceCannotBeNullException;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class CakeService {

	@Autowired
	private ICakeDao cakeDao;

	@Autowired
	private ModelMapper modelMapper;

	public static final Logger logger = Logger.getLogger(CakeService.class);

	public Cake addCake(CakeDTO cakeDTO) throws ResourceCannotBeNullException {
		if (cakeDTO.getCakeName() == null)
			throw new ResourceCannotBeNullException("Cake name cannot be empty");
		Cake cakeToBeSaved = modelMapper.map(cakeDTO, Cake.class);
		return cakeDao.save(cakeToBeSaved);
	}

	public CakeOccasions[] getAllOccasions() {
		return CakeOccasions.values();
	}

	public CakeCategory[] getAllCategories() {
		return CakeCategory.values();
	}

	public CakeFlavours[] getAllFlavours() {
		return CakeFlavours.values();
	}

	public List<Cake> addCakeList(List<CakeDTO> cakeDTOList) {
		List<Cake> cakesList = cakeDTOList.stream().map(cakeDto -> modelMapper.map(cakeDto, Cake.class))
				.collect(Collectors.toList());
		return cakeDao.saveAll(cakesList);
	}

	public List<CakeDTO> getAllCakes() {
		List<Cake> cakesList = cakeDao.findAll();
		return cakesList.stream().map(cake -> modelMapper.map(cake, CakeDTO.class)).collect(Collectors.toList());
	}

	public CakeDTO findCakeById(long id) {
		Optional<Cake> cakeOptional = cakeDao.findById(id);
		if (cakeOptional.isPresent()) {
			Cake cakeToFind = cakeOptional.get();
			logger.info("Found Cake: " + cakeToFind);
			return modelMapper.map(cakeToFind, CakeDTO.class);
		} else {
			throw new ResourceNotFoundException("Cake(id) does not exist");
		}
	}

	public List<Cake> findCakeByCategory(CakeCategory category) {
		return cakeDao.findByCategory(category);
	}

	public List<Cake> findCakeByOccasions(CakeOccasions occasions) {
		return cakeDao.findByOccasions(occasions);
	}

	public List<Cake> findCakeByFlavours(CakeFlavours flavours) {
		return cakeDao.findByFlavours(flavours);
	}

	public CakeDTO deleteCakeById(long id) throws ResourceNotFoundException {
		Optional<Cake> cakeOptional = cakeDao.findById(id);
		if (cakeOptional.isPresent()) {
			Cake cakeToDelete = cakeOptional.get();
			cakeDao.deleteById(id);
			logger.info("Deleted cake: " + cakeToDelete + " for ID: " + id);
			return modelMapper.map(cakeToDelete, CakeDTO.class);
		} else {
			throw new ResourceNotFoundException("Cake(id) does not exist");
		}
	}

	public Map<String, Cake> editCakeById(long id, CakeDTO cakeDto) {
		Optional<Cake> cakeToFind = cakeDao.findById(id);
		if (cakeToFind.isEmpty()) {
			throw new ResourceNotFoundException("Cake with " + id + " does not exist");
		} else {
			cakeDto.setId(id);
			Cake cake = modelMapper.map(cakeDto, Cake.class);
			cake = cakeDao.save(cake);
			logger.info("Cake: " + cake + " for Id: " + id + " updated.");
			return Collections.singletonMap("Cake updated", cake);
		}
	}

}
