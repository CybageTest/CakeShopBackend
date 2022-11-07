package com.littlejoys.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addons")
public class AddOns {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private double price;
	private String description;

	@ManyToMany(mappedBy = "addons", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Order> order;

	public AddOns() {

	}

	public AddOns(long id, String name, double price, String description,  Set<Order> order) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.order = order;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
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

	@Override
	public String toString() {
		return "AddOns [id=" + id + ", name=" + name + ", price=" + price + ", description=" + description
				+ ", order=" + order + "]";
	}

}
