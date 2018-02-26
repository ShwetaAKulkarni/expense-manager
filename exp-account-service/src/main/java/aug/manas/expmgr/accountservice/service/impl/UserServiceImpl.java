/**
 * 
 */
package aug.manas.expmgr.accountservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aug.manas.expmgr.accountservice.model.User;
import aug.manas.expmgr.accountservice.repository.UserRepository;
import aug.manas.expmgr.accountservice.service.UserService;

/**
 * @author shweta
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	/*
	 * / (non-Javadoc)
	 * 
	 * @see aug.manas.accountservice.service.UserService#findById(long) Method
	 * to find User bby userId
	 * 
	 * @Param long userId
	 */
	@Override
	public User findById(Long userId) {

		logger.debug("Finding User by userId " + userId);
		User user = null;
		if (userId != null) {
			user = userRepository.findOne(userId);
			if (user == null) {
				logger.error("Cannot find user with userId "+userId);
			}
		} else {
			logger.error("UserId is null");
		}
		return user;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * aug.manas.accountservice.service.UserService#findByUsername(java.lang.
	 * String) Method to find user by username
	 * 
	 * @Param String username
	 */

	public User findByUsername(String username) {

		logger.debug("Finding User by username " + username);
		User user = null;
		if (username != null && !username.isEmpty()) {
			user = userRepository.findByUsername(username);
			if (user == null) {
				logger.error("User obbject is null");
			}
		} else {
			logger.error("User id is less than 1");
		}
		return user;

	}

	@Override
	public User findUserByEmail(String email) {
		logger.debug("Finding User by email " + email);
		User user = null;
		if (email != null && !email.isEmpty()) {
			user = userRepository.findByEmail(email);
			if (user == null) {
				logger.error("User obbject is null");
			}
		} else {
			logger.error("User id is less than 1");
		}
		return user;
	}

	@Override
	public User saveUser(User user) {
		logger.debug("Saving user");
		User savedUser = null;
		if (user != null) {
			savedUser = userRepository.save(user);

		} else {
			logger.error("User object is null");
		}
		return savedUser;

	}

	@Override
	public User updateUser(User user) {
		logger.debug("Updating  user");
		User updatedUser = null;
		if (user != null) {
			if (user.getId() != null && isUserExist(user)) {
				updatedUser = saveUser(user);
			} else {
				logger.error("Cannot update user with userID " + user.getId() + " and username " + user.getUsername());
			}

		} else {
			logger.error("User object is null");
		}

		return updatedUser;

	}

	@Override
	public User createUser(User user) {
		logger.debug("Creating new User");
		User createdUser = null;
		if (user != null) {
			if (findById(user.getId()) == null && !isUserExist(user)) {
				createdUser = saveUser(user);
			} else {
				logger.error("User already exist");
			}
		} else {
			logger.error("User object is null");
		}
		return createdUser;

	}

	@Override
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public boolean isUserExist(User user) {
		logger.debug("Check if user already exists");
		return findByUsername(user.getUsername()) != null || findUserByEmail(user.getEmail()) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aug.manas.accountservice.service.UserService#deactivateUser(long)
	 */
	@Override
	public boolean deactivateUser(Long userId) {
		logger.debug("Deactivating user account");
		 boolean isActive = true;
		if(userId != null){
			User userToDeactivate = findById(userId);
			if(userToDeactivate !=null){
				userToDeactivate.setActive(false);
				isActive = userToDeactivate.isActive();
			}
		}
		return isActive;
	}

}
