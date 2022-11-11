package com.littlejoys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cakes")
public class Cake {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String cakeName;
	private double price;
	@Column(columnDefinition = "varchar(255) default 'Preparation starts after receiving the order. No storage.' ")
	private String description;
	private int weight;

	@Enumerated(EnumType.STRING)
	private CakeFlavours flavours;

	@Enumerated(EnumType.STRING)
	private CakeCategory category;

	@Enumerated(EnumType.STRING)
	private CakeOccasions occasions;

	@ManyToMany(mappedBy = "cakes", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Order> order;

}
