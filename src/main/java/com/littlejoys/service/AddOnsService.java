	package com.littlejoys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IAddOnsDao;
import com.littlejoys.dto.AddOnsDTO;
import com.littlejoys.entity.AddOns;
import com.littlejoys.exception.ResourceCannotBeNullException;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class AddOnsService {

	public static final Logger logger = Logger.getLogger(AddOnsService.class);

	@Autowired
	private IAddOnsDao addonDao;

	@Autowired
	private ModelMapper modelMapper;

	public AddOns addAddOns(AddOnsDTO addOnsDTO) {
		if (addOnsDTO.getName() == null) {
			throw new ResourceCannotBeNullException("AddOn name cannot be empty");
		}
		AddOns addOnsToBeSaved = modelMapper.map(addOnsDTO, AddOns.class);
		return addonDao.save(addOnsToBeSaved);
	}

	public List<AddOns> addAddonsList(List<AddOnsDTO> addOnsDTOList) {
		List<AddOns> addOnsList = addOnsDTOList.stream().map(addOn -> modelMapper.map(addOn, AddOns.class))
				.collect(Collectors.toList());
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
		List<AddOns> addOnsList = addonDao.findAll();
		return addOnsList.stream().map(addOn -> modelMapper.map(addOn, AddOnsDTO.class)).collect(Collectors.toList());
	}

}
