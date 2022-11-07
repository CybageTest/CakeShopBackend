package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.entity.AddOns;

@Service
public class AddOnsService {

	@Autowired
	private IAddOnsDao addonDao;

	public AddOns addAddOns(AddOns addOns) {
		return addonDao.save(addOns);
	}

}
