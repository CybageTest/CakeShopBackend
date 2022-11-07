package com.littlejoys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.AddOns;

@Repository
public interface IAddOnsDao extends JpaRepository<AddOns, Long>{
	
}
