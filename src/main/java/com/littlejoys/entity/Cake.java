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

	public Cake() {

	}

	public Cake(long id, String cakeName, double price, String description, int weight, CakeFlavours flavours,
			CakeCategory category, CakeOccasions occasions, Set<Order> order) {
		super();
		this.id = id;
		this.cakeName = cakeName;
		this.price = price;
		this.description = description;
		this.weight = weight;
		this.flavours = flavours;
		this.category = category;
		this.occasions = occasions;
		this.order = order;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCakeName() {
		return cakeName;
	}

	public void setCakeName(String cakeName) {
		this.cakeName = cakeName;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public CakeFlavours getFlavours() {
		return flavours;
	}

	public void setFlavours(CakeFlavours flavours) {
		this.flavours = flavours;
	}

	public CakeCategory getCategory() {
		return category;
	}

	public void setCategory(CakeCategory category) {
		this.category = category;
	}

	public CakeOccasions getOccasions() {
		return occasions;
	}

	public void setOccasions(CakeOccasions occasions) {
		this.occasions = occasions;
	}

	public Set<Order> getOrder() {
		return order;
	}

	public void setOrder(Set<Order> order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Cake [id=" + id + ", cakeName=" + cakeName + ", price=" + price + ", description=" + description
				+ ", weight=" + weight + ", flavours=" + flavours + ", category=" + category + ", occasions="
				+ occasions + ", order=" + order + "]";
	}

}
