package com.littlejoys.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.littlejoys.entity.Role;
import com.littlejoys.service.RoleService;

class RoleControllerTest {

	@InjectMocks
	private RoleController roleController;

	@Mock
	private RoleService roleService;

	private Role role;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		role = new Role("Testeruser", "Testing user description");
	}

	@Test
	void testCreateNewRole() {
		Mockito.when(roleService.createNewRole(Mockito.any(Role.class))).thenReturn(role);

		Role createdRole = roleController.createNewRole(role);

		assertNotNull(createdRole);
		assertEquals(role.getRoleName(), createdRole.getRoleName());
		assertEquals(role.getRoleDescription(), createdRole.getRoleDescription());
		Mockito.verify(roleService, Mockito.times(1)).createNewRole(Mockito.any(Role.class));
	}
}
