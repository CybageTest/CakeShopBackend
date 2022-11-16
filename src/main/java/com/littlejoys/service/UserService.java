package com.littlejoys.service;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.littlejoys.dao.IConfirmationTokenDao;
import com.littlejoys.dao.IRoleDao;
import com.littlejoys.dao.IUserDao;
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

	public User createNewUser(User user) throws Exception, SQLException {
		if (user.getStatus() != null) {
			user.setStatus("active");
			return userDao.save(user);
		} else {

			Role role = roleDao.findById("user").get();

			Set<Role> roles = new HashSet<>();
			roles.add(role);
			User userToBeFoundByName = userDao.findByName(user.getName());
			User userToBeFoundByMobile = userDao.findByMobile(user.getMobile());
			User userToBeFoundByEmail = userDao.findByEmail(user.getEmail());

			if (userToBeFoundByName != null) {
				throw new ResourceAlreadyExistException("A user already exist with this account");
			} else if (userToBeFoundByEmail != null) {
				throw new ResourceAlreadyExistException("A user with this email already exist");
			} else if (userToBeFoundByMobile != null) {
				throw new ResourceAlreadyExistException("A user with that mobile already exist");
			} else {
				user.setStatus("inactive");
				user.setRole(roles);
				user.setPassword(getEncodedPassword(user.getPassword()));
				createAndSendConfirmationTokenViaEmail(user);
				return userDao.save(user);
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

	public String confirmUserAccount(String confirmationToken) throws SQLException, Exception {
		ConfirmationToken token = confirmationTokenDao.findByConfirmationToken(confirmationToken);
		if (token != null) {
			User user = userDao.findByEmail(token.getUser().getEmail());
			createNewUser(user);
			return "Succesful";
		}
		return "Registration Failed";
	}

	public User findUserByEmailOrMobile(String email, String mobile) {
		return userDao.findByEmailOrMobile(email, mobile);
	}
	
	public User changeUserPassword(String name, String oldPassword, String newPassword) throws Exception {
		User loggedInUser= userDao.findByName(name);
		if(loggedInUser!=null) {
			if(checkIfValidOldPassword(loggedInUser, oldPassword)) {
				loggedInUser.setPassword(passwordEncoder.encode(newPassword));
				userDao.save(loggedInUser);
				return loggedInUser;
			}else {
				throw new InvalidOldPasswordException("Old password does not match");
			}
		}
		throw new ResourceNotFoundException("User(name) does not exist");
	}
	
	public Boolean checkIfValidOldPassword(User loggedInUser, String oldPasswordToMatch ) {
		if(passwordEncoder.matches(oldPasswordToMatch, loggedInUser.getPassword())){
			return true;
		}
		return false;
	}

	public User getUserById(String name) {
		return userDao.findById(name).orElseThrow(()->new ResourceNotFoundException("User does not exist"));
	}

}
