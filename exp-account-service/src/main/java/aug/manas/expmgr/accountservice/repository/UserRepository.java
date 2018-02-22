/**
 * 
 */
package aug.manas.expmgr.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import aug.manas.expmgr.accountservice.model.User;

/**
 * @author shweta
 *
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	User findByUsername(String username);

}
