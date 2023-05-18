package com.littlejoys.service;

import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IConfirmationTokenDao;
import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
import com.littlejoys.dto.UserDTO;
import com.littlejoys.entity.ConfirmationToken;
import com.littlejoys.entity.Role;
import com.littlejoys.entity.User;
import com.littlejoys.exception.InvalidOldPasswordException;
import com.littlejoys.exception.ResourceAlreadyExistException;
import com.littlejoys.exception.ResourceNotFoundException;

@Service
public class UserService {
	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	private IConfirmationTokenDao confirmationTokenDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	public void initRolesAndUser() {
		Role adminRole = new Role("admin", "Admin Role");
		roleDao.save(adminRole);

		Role userRole = new Role("user", "Default role for newly created");
		roleDao.save(userRole);

		Set<Role> adminRoles = new HashSet<>();
		adminRoles.add(adminRole);

		Set<Role> userRoles = new HashSet<>();
		userRoles.add(userRole);

		User adminUser = new User("admin", "admin@mail.com", getEncodedPassword("admin@123"), "9850909009", "active",
				null, adminRoles, null);
		userDao.save(adminUser);

//		User sampleUser= new User("abhi", "abhi@mail.com", getEncodedPassword("abhi@123"), "1234567890", "active", userRoles);		
//		userDao.save(sampleUser);

	}

	public String getEncodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	public User createNewUser(UserDTO userDTO) throws ResourceAlreadyExistException, MessagingException {
		User userToBeSaved = modelMapper.map(userDTO, User.class);
		if (userDTO.getStatus() != null) {
			userToBeSaved.setStatus("active");
			return userDao.save(userToBeSaved);
		} else {

			Role role = roleDao.findById("user").get();
			Set<Role> roles = new HashSet<>();
			roles.add(role);

			User userToBeFoundByName = userDao.findByName(userToBeSaved.getName());
			User userToBeFoundByMobile = userDao.findByMobile(userToBeSaved.getMobile());
			User userToBeFoundByEmail = userDao.findByEmail(userToBeSaved.getEmail());

			if (userToBeFoundByName != null) {
				throw new ResourceAlreadyExistException("A user already exist with this account");
			} else if (userToBeFoundByEmail != null) {
				throw new ResourceAlreadyExistException("A user with this email already exist");
			} else if (userToBeFoundByMobile != null) {
				throw new ResourceAlreadyExistException("A user with that mobile already exist");
			} else {
				userToBeSaved.setStatus("inactive");
				userToBeSaved.setRole(roles);
				userToBeSaved.setPassword(getEncodedPassword(userToBeSaved.getPassword()));
				createAndSendConfirmationTokenViaEmail(userToBeSaved);
				return userDao.save(userToBeSaved);
			}
		}
	}

	public void createAndSendConfirmationTokenViaEmail(User user) throws MessagingException {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);
		confirmationTokenDao.save(confirmationToken);
		String emailBody = ("To confirm your account, please click here : "
				+ "http://172.27.232.112:8080/api/user/confirm-account?token="
				+ confirmationToken.getConfirmationToken());
		String emailSubject = "Complete Registration!";
		emailService.sendEmail(user.getEmail(), emailSubject, emailBody);
	}

	public String confirmUserAccount(String confirmationToken)
			throws ResourceAlreadyExistException, MessagingException {
		ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(confirmationToken);
		if (token != null) {
			User user = userDao.findByEmail(token.getUser().getEmail());
			UserDTO userToAdd = modelMapper.map(user, UserDTO.class);
			createNewUser(userToAdd);
			return "Succesful";
		}
		return "Registration Failed";
	}

	public UserDTO findUserByEmailOrMobile(String email, String mobile) {
		User userToBeFound = userDao.findByEmailOrMobile(email, mobile);
		return modelMapper.map(userToBeFound, UserDTO.class);
	}

	public Boolean checkIfValidOldPassword(User loggedInUser, String oldPasswordToMatch) {
		return passwordEncoder.matches(oldPasswordToMatch, loggedInUser.getPassword());
	}

	public UserDTO changeUserPassword(String name, String oldPassword, String newPassword)
			throws InvalidOldPasswordException {
		User loggedInUser = userDao.findByName(name);
		if (loggedInUser != null) {
			if (Boolean.TRUE.equals(checkIfValidOldPassword(loggedInUser, oldPassword))) {
				loggedInUser.setPassword(passwordEncoder.encode(newPassword));
				userDao.save(loggedInUser);
				return modelMapper.map(loggedInUser, UserDTO.class);
			} else {
				throw new InvalidOldPasswordException("Old password does not match");
			}
		}
		throw new ResourceNotFoundException("User(name) does not exist");
	}

	public UserDTO getUserById(String name) {
		User userToFind = userDao.findByName(name);
		if (userToFind != null) {
			return modelMapper.map(userToFind, UserDTO.class);
		} else {
			throw new ResourceNotFoundException("User does not exist");
		}
	}

	public String enableUser(String userName) throws ResourceAlreadyExistException {
		User userToBeFound = userDao.findByName(userName);
		if (userToBeFound != null) {
			if (userToBeFound.getStatus().equals("active")) {
				throw new ResourceAlreadyExistException("Account is already active for " + userName);
			}
			userToBeFound.setStatus("active");
			userDao.save(userToBeFound);
			return "Status changed to active for " + userName;
		} else {
			throw new ResourceNotFoundException("User does not exist..!");
		}
	}
	
	public String disableUser(String userName) throws ResourceAlreadyExistException {
		User userToBeFound = userDao.findByName(userName);
		if (userToBeFound != null) {
			if (userToBeFound.getStatus().equals("inactive")) {
				throw new ResourceAlreadyExistException("Account is already disabled for " + userName);
			}
			userToBeFound.setStatus("inactive");
			userDao.save(userToBeFound);
			return "Status changed to inactive for " + userName;
		} else {
			throw new ResourceNotFoundException("User does not exist..!");
		}
	}

}
