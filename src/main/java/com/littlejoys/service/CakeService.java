package com.littlejoys.service;

import java.util.ArrayList;
import java.util.List;

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
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class CakeService {

	@Autowired
	private ICakeDao cakeDao;

	@Autowired
	private ModelMapper modelMapper;

	public static final Logger logger = Logger.getLogger(CakeService.class);

	public CakeDTO addCake(CakeDTO cakeDTO) {
		Cake cake = modelMapper.map(cakeDTO, Cake.class);
		Cake savedCake = cakeDao.save(cake);
		logger.info("Cake added: " + savedCake);
		return modelMapper.map(savedCake, CakeDTO.class);
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

	public List<Cake> addCakeList(List<Cake> cakeList) {
		return cakeDao.saveAll(cakeList);
	}

	public List<CakeDTO> getAllCakes() {
		List<CakeDTO> cakeDTOList = new ArrayList<>();
		List<Cake> cakesList = cakeDao.findAll();
		for (Cake cake : cakesList) {
			CakeDTO cakeDTO = modelMapper.map(cake, CakeDTO.class);
			cakeDTOList.add(cakeDTO);
		}
		return cakeDTOList;
	}

	public CakeDTO findCakeById(long id) {
		CakeDTO cakeDTO = modelMapper.map(cakeDao.findById(id), CakeDTO.class);
		if (cakeDTO != null) {
			logger.info("Found Cake: " + cakeDTO + " for ID: " + id);
			return cakeDTO;
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
		CakeDTO cakeToDelete = findCakeById(id);
		if (cakeToDelete != null) {
			cakeDao.deleteById(id);
			logger.info("Deleted cake: " + cakeToDelete + " for ID: " + id);
			return cakeToDelete;
		}
		throw new ResourceNotFoundException("Cake(id) does not exist");
	}

	public Cake editCakeById(long id, Cake cake) {
		Cake cakeToEdit = findCakeById(id);
		if (cakeToEdit != null) {
			cakeDao.save(cake);
			return cakeToEdit;
		}
		throw new ResourceNotFoundException("Cake(id) does not exist");
	}

}
