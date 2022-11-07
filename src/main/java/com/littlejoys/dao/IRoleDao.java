package com.littlejoys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.Role;

@Repository
public interface IRoleDao extends JpaRepository<Role, String> {
	
}
