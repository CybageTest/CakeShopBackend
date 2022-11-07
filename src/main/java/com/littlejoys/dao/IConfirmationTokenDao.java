package com.littlejoys.dao;

import org.springframework.data.repository.CrudRepository;

import com.littlejoys.entity.ConfirmationToken;

public interface IConfirmationTokenDao extends CrudRepository<ConfirmationToken, String> {
	ConfirmationToken findByConfirmationToken(String confirmationToken);
}
