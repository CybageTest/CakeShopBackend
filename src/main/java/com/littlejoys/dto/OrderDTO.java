package com.littlejoys.dto;

import java.util.Set;

import com.littlejoys.entity.AddOns;
import com.littlejoys.entity.Cake;
import com.littlejoys.entity.Offer;
import com.littlejoys.entity.User;

public class OrderDTO {

	private long id;
	private String messageOnCake;
	private Double shippingCost;
	private Double totalCost;
	private String address;
	private float weight;
	private Set<Cake> cakes;
	private User user;
	private Set<Offer> offer;
	private Set<AddOns> addons;

	public OrderDTO() {

	}

	public OrderDTO(long id, String messageOnCake, Double shippingCost, Double totalCost, String address, float weight,
			Set<Cake> cakes, User user, Set<Offer> offer, Set<AddOns> addons) {
		super();
		this.id = id;
		this.messageOnCake = messageOnCake;
		this.shippingCost = shippingCost;
		this.totalCost = totalCost;
		this.address = address;
		this.weight = weight;
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

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public void setShippingCost(Double shippingCost) {
		this.shippingCost = shippingCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "OrderDTO [id=" + id + ", messageOnCake=" + messageOnCake + ", shippingCost=" + shippingCost
				+ ", totalCost=" + totalCost + ", address=" + address + ", weight=" + weight + ", cakes=" + cakes
				+ ", user=" + user + ", offer=" + offer + ", addons=" + addons + "]";
	}

}
