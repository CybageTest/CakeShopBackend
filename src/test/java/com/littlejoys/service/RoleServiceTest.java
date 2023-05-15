package com.littlejoys.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.littlejoys.dao.IRoleDao;
import com.littlejoys.entity.Role;

class RoleServiceTest {

	@Mock
	private IRoleDao roleDao;

	@InjectMocks
	private RoleService roleService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testCreateNewRole() {
		Role testRole = new Role();
		testRole.setRoleName("Tester");
		testRole.setRoleDescription("Testing default test user");
		when(roleDao.save(testRole)).thenReturn(testRole);

		// Call the createNewRole method
		Role result = roleService.createNewRole(testRole);

		// Verify that the RoleDao save method was called with the Role object
		verify(roleDao, times(1)).save(testRole);

		// Verify that the returned Role object is the same as the one saved
		assertEquals(testRole, result);
	}

}
