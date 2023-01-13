package com.littlejoys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class AddOnsService {

	public static final Logger logger = Logger.getLogger(AddOnsService.class);

	@Autowired
	private IAddOnsDao addonDao;

	@Autowired
	private ModelMapper modelMapper;

	public AddOnsDTO addAddOns(AddOnsDTO addOnsDTO) {
		AddOns addOns = modelMapper.map(addOnsDTO, AddOns.class);
		AddOns savedAddOn = addonDao.save(addOns);
		logger.info("Added AddOn: " + savedAddOn);
		return modelMapper.map(savedAddOn, AddOnsDTO.class);
	}

	public List<AddOns> addAddonsList(List<AddOns> addOnsList) {
		return addonDao.saveAll(addOnsList);
	}

	public AddOnsDTO findAddOnById(long id) {
		AddOnsDTO addOnsDTO = modelMapper.map(addonDao.findById(id), AddOnsDTO.class);
		if (addOnsDTO != null) {
			logger.info("Found AddOn: " + addOnsDTO + " for ID: " + id);
			return addOnsDTO;
		} else {
			throw new ResourceNotFoundException("Addon(id) does not exist");
		}
	}

	public AddOnsDTO deleteAddOnById(long id) throws ResourceNotFoundException {
		AddOnsDTO addOnsToDelete = findAddOnById(id);
		if (addOnsToDelete != null) {
			addonDao.deleteById(id);
			logger.info("AddOn deleted: " + addOnsToDelete);
			return addOnsToDelete;
		}
		throw new ResourceNotFoundException("Addon(id) does not exist");
	}

	public Map<String, Object> editAddOnById(long id, AddOnsDTO addOnsDTO) {
		if (addonDao.existsById(id)) {
			addOnsDTO.setId(id);
			AddOns addOns = modelMapper.map(addOnsDTO, AddOns.class);
			addOns = addonDao.save(addOns);
			logger.info("AddOn updated: " + addOns);
			return Collections.singletonMap("AddOns updated", addOns);
		}
		return Collections.singletonMap("AddOns updation failed", 0);
	}

	public List<AddOnsDTO> getAllAddOns() {
		List<AddOnsDTO> addOnsDTOList = new ArrayList<>();
		List<AddOns> addOnsList = addonDao.findAll();
		for (AddOns addOn : addOnsList) {
			AddOnsDTO addOnsDTO = modelMapper.map(addOn, AddOnsDTO.class);
			addOnsDTOList.add(addOnsDTO);
		}
		return addOnsDTOList;
	}

}
