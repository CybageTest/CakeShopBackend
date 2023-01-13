package com.littlejoys.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "offers")
public class Offer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String code;
	private int discount;
	private String description;

	@ManyToMany(mappedBy = "offer")
	@JsonIgnore
	private Set<Order> order;

	public Offer() {

	}

	public Offer(long id, String name, String code, int discount, String description, Set<Order> order) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.discount = discount;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
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
		return "Offer [id=" + id + ", name=" + name + ", code=" + code + ", discount=" + discount + ", description="
				+ description + ", order=" + order + "]";
	}

}
