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

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String messageOnCake;
	private Double shippingCost;
	private Double totalCost;
	private String address;

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Cake> cakes;

	@OneToOne(cascade = CascadeType.MERGE)
	private User user;

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<Offer> offer;

	@ManyToMany(cascade = CascadeType.MERGE)
	private Set<AddOns> addons;

	public Order() {

	}

	public Order(long id, String messageOnCake, double shippingCost, double totalCost, String address, Set<Cake> cakes,
			User user, Set<Offer> offer, Set<AddOns> addons) {
		super();
		this.id = id;
		this.messageOnCake = messageOnCake;
		this.shippingCost = shippingCost;
		this.totalCost = totalCost;
		this.address = address;
		this.cakes = cakes;
		this.user = user;
		this.offer = offer;
		this.addons = addons;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessageOnCake() {
		return messageOnCake;
	}

	public void setMessageOnCake(String messageOnCake) {
		this.messageOnCake = messageOnCake;
	}

	public double getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Set<Cake> getCakes() {
		return cakes;
	}

	public void setCakes(Set<Cake> cakes) {
		this.cakes = cakes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Offer> getOffer() {
		return offer;
	}

	public void setOffer(Set<Offer> offer) {
		this.offer = offer;
	}

	public Set<AddOns> getAddons() {
		return addons;
	}

	public void setAddons(Set<AddOns> addons) {
		this.addons = addons;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", messageOnCake=" + messageOnCake + ", shippingCost=" + shippingCost
				+ ", totalCost=" + totalCost + ", address=" + address + ", cakes=" + cakes + ", user=" + user
				+ ", offer=" + offer + ", addons=" + addons + "]";
	}

}
