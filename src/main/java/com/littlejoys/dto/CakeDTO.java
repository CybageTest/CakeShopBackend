package com.littlejoys.dto;

import java.util.Set;

import com.littlejoys.entity.CakeCategory;
import com.littlejoys.entity.CakeFlavours;
import com.littlejoys.entity.CakeOccasions;
import com.littlejoys.entity.Order;

public class CakeDTO {

	private long id;
	private String cakeName;
	private double price;
	private String description;
	private int weight;
	private CakeFlavours flavours;
	private CakeCategory category;
	private CakeOccasions occasions;
	private Set<Order> order;

	public CakeDTO() {

	}

	public CakeDTO(long id, String cakeName, double price, String description, int weight, CakeFlavours flavours,
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
