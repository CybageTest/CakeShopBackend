package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.ICakeDao;
import com.littlejoys.entity.Cake;

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
	

}
