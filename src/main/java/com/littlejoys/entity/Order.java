package com.littlejoys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String messageOnCake;
	public double shippingCost;
	public double totalCost;
	public String address;

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Cake> cakes;

	@OneToOne(cascade = CascadeType.MERGE)
	private User user;

	@OneToOne(cascade = CascadeType.MERGE)
	private Offer offer;

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<AddOns> addons;

}
