package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class CakeService {

	@Autowired
	private ICakeDao cakeDao;

	public Cake addCake(Cake cake) {
		return cakeDao.save(cake);
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
	

}
