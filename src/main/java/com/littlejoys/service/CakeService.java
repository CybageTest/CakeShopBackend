package com.littlejoys.service;

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


}
