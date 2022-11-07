package com.littlejoys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class AddOnsService {

	@Autowired
	private IAddOnsDao addonDao;

	public AddOns addAddOns(AddOns addOns) {
		return addonDao.save(addOns);
	}

	public List<AddOns> addAddonsList(List<AddOns> addOnsList) {
		return addonDao.saveAll(addOnsList);
	}

	public AddOns findAddOnById(long id) {
		return addonDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Addon(id) does not exist"));
	}

	public AddOns deleteAddOnById(long id) throws Exception {
		AddOns addOnsToDelete = findAddOnById(id);
		if (addOnsToDelete != null) {
			addonDao.deleteById(id);
			return addOnsToDelete;
		}
		throw new ResourceNotFoundException("Addon(id) does not exist");
	}

	public AddOns editAddon(long id, AddOns addon) throws Exception {
		AddOns addOnsToEdit = findAddOnById(id);
		if (addOnsToEdit != null) {
			addonDao.save(addon);
			return addOnsToEdit;
		}
		throw new ResourceNotFoundException("Addon(id) does not exist");
	}

}
