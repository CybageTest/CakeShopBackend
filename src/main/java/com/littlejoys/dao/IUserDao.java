package com.littlejoys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.User;

@Repository
public interface IUserDao extends JpaRepository<User, String>{
	User findByName(String name);
	User findByMobile(String mobile);
	User findByEmail(String email);
	User findByEmailOrMobile(String email, String mobile);
}
