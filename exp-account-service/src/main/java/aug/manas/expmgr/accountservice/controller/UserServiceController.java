/**
 * 
 */
package aug.manas.expmgr.accountservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import aug.manas.expmgr.accountservice.model.User;
import aug.manas.expmgr.accountservice.service.UserService;
import aug.manas.expmgr.accountservice.util.ExpAccountServiceException;

/**
 * @author shweta
 *
 */
@RestController
@RequestMapping("/user")
public class UserServiceController {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		logger.info("Getting list of all users");
		List<User> users = userService.findAllUsers();

		if (users != null && users.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	/**
	 * Get User Information
	 * @param userId
	 * @return
	 * @throws ExpAccountServiceException
	 */
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable("userId") long userId) throws ExpAccountServiceException {
		logger.info("Fetching User with userId {}", userId);
		User user = userService.findById(userId);
		if (user == null) {
			logger.error("User with id {} not found.", userId);
			throw new ExpAccountServiceException("User with id " + userId + " doesn´t exist");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	/**
	 * Create a new User
	 * @param user
	 * @param ucBuilder
	 * @return
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder)
			throws ExpAccountServiceException {
		logger.info("Creating User : {}", user);

		if (userService.isUserExist(user)) {
			logger.error("Unable to create. A User with username {} already exist", user.getUsername());
			throw new ExpAccountServiceException(
					"Unable to create. A User with name " + user.getUsername() + " already exist.");
		}
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
	}

	/**
	 * Update the user
	 * @param userId
	 * @param user
	 * @return
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable("userId") long userId, @RequestBody User user)
			throws ExpAccountServiceException {
		logger.info("Updating User with id {}", userId);

		User currentUser = userService.findById(userId);

		if (currentUser == null) {
			logger.error("Unable to update. User with id {} not found.", userId);
			throw new ExpAccountServiceException("Unable to upate. User with id " + userId + " not found.");

		}

		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		currentUser.setUsername(user.getUsername());
		currentUser.setEmail(user.getEmail());
		currentUser.setPassword(user.getPassword());

		userService.updateUser(currentUser);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	/***
	 * Deactivating the user
	 * @param userId
	 * @return
	 * @throws ExpAccountServiceException
	 */
	//check this method logic 
	@RequestMapping(value = "/deactivate/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<?> deactivateUser(@PathVariable("userId") long userId) throws ExpAccountServiceException {
		logger.info("Fetching & Deactivating User with id {}", userId);

		User user = userService.findById(userId);
		if (user == null) {
			logger.error("Unable to delete. User with id {} not found.", userId);
			throw new ExpAccountServiceException("Unable to deactivate. User with id " + userId + " not found.");
		}
		boolean isActive = userService.deactivateUser(userId);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

//	@RequestMapping(value = "/deleteAll", method = RequestMethod.DELETE)
//	public ResponseEntity<User> deleteAllUsers() {
//		logger.info("Deleting All Users");
//
//		userService.deleteAllUsers();
//		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
//	}

}
