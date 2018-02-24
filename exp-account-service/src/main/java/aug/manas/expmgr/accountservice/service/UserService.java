/**
 * 
 */
package aug.manas.expmgr.accountservice.service;

import java.util.List;

import aug.manas.expmgr.accountservice.model.User;

/**
 * @author shweta
 *
 */
public interface UserService {

	User findById(Long userId);

	User findByUsername(String username);

	User findUserByEmail(String email);

	User createUser(User user);

	User saveUser(User user);

	User updateUser(User user);

	List<User> findAllUsers();

	boolean deactivateUser(Long userId);

	boolean isUserExist(User user);

}
