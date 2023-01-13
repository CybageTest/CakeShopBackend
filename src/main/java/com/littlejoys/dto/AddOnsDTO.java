package com.littlejoys.dto;

import java.util.Set;

import com.littlejoys.entity.Order;


public class AddOnsDTO {

	private long id;
	private String name;
	private double price;
	private String description;
	private Set<Order> order;

	public AddOnsDTO() {

	}

	public AddOnsDTO(long id, String name, double price, String description, Set<Order> order) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.order = order;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "AddOns [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description + ", order="
				+ order + "]";
	}

}
