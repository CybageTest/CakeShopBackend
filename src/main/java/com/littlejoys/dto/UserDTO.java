package com.littlejoys.dto;

import java.util.Set;

import com.littlejoys.entity.Order;
import com.littlejoys.entity.Role;


public class UserDTO {

	private String name;
	private String email;
	private String password;
	private String mobile;
	private String status;
	private String profileImageLink;
	private Set<Role> role;
	private Order order;

	public UserDTO() {

	}

	public UserDTO(String name, String email, String password, String mobile, String status, String profileImageLink,
			Set<Role> role, Order order) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.status = status;
		this.profileImageLink = profileImageLink;
		this.role = role;
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProfileImageLink() {
		return profileImageLink;
	}

	public void setProfileImageLink(String profileImageLink) {
		this.profileImageLink = profileImageLink;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", email=" + email + ", password=" + password + ", mobile=" + mobile + ", status="
				+ status + ", profileImageLink=" + profileImageLink + ", role=" + role + ", order=" + order + "]";
	}

}