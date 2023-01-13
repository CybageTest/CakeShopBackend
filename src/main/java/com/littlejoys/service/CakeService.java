package com.littlejoys.service;

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

	public List<Cake> getAllCakes() {
		return cakeDao.findAll();
	}

	public Cake findCakeById(long id) {
		return cakeDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cake(id) does not exist"));
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

	public Cake deleteCakeById(long id) throws Exception {
		Cake cakeToDelete = findCakeById(id);
		if (cakeToDelete != null) {
			cakeDao.deleteById(id);
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
