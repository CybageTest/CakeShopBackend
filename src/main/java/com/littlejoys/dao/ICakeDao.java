package com.littlejoys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.Cake;
import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;

@Repository
public interface ICakeDao extends JpaRepository<Cake, Long>{

	List<Cake> findByCategory(CakeCategory category);
	
	List<Cake> findByOccasions(CakeOccasions occasions);
	
	List<Cake> findByFlavours(CakeFlavours flavours);
}
