package com.littlejoys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.littlejoys.entity.Offer;

@Repository
public interface IOfferDao extends JpaRepository<Offer, Long>{

}
