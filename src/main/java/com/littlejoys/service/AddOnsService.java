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
		Optional<AddOns> addOnOptional = addonDao.findById(id);
		if (addOnOptional.isPresent()) {
			AddOns addOnToDelete = addOnOptional.get();
			addonDao.deleteById(id);
			logger.info("Deleted cake: " + addOnToDelete + " for ID: " + id);
			return modelMapper.map(addOnToDelete, AddOnsDTO.class);
		} else {
			throw new ResourceNotFoundException("AddOns(id) does not exist");
		}
	}

	public Map<String, Object> editAddOnById(long id, AddOnsDTO addOnsDTO) {
		Optional<AddOns> addOnToFind = addonDao.findById(id);
		if (addOnToFind.isEmpty()) {
			throw new ResourceNotFoundException("AddOn with " + id + " does not exist");
		} else {
			addOnsDTO.setId(id);
			AddOns addOn = modelMapper.map(addOnsDTO, AddOns.class);
			addOn = addonDao.save(addOn);
			logger.info("AddOn: " + addOn + " for Id: " + id + " updated.");
			return Collections.singletonMap("AddOn updated", addOn);
		}
	}

	public List<AddOnsDTO> getAllAddOns() {
		List<AddOns> addOnsList = addonDao.findAll();
		return addOnsList.stream().map(addOn -> modelMapper.map(addOn, AddOnsDTO.class)).collect(Collectors.toList());
	}

}
