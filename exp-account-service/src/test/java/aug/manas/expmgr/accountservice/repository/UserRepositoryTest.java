/**
 * 
 */
package aug.manas.expmgr.accountservice.repository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import aug.manas.expmgr.accountservice.model.User;
import aug.manas.expmgr.accountservice.repository.UserRepository;

/**
 * @author shweta
 *
 */

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

	@Autowired
	private UserRepository repository;

	private User user1;
	private User user2;

	@Before
	public void setup() {
		user1 = new User("John", "Kepler", "johnk1", "1234534", "johnk@email.com");
		user1.setId(1L);
		user2 = new User("Sarah", "Jackson", "jacksonsarah", "724sdvs34", "sarahjackson@email.com");
		user2.setId(2L);
	}

	/*
	 * Test to find user by email address
	 */
	@Test
	public void should_find_user_ByEmail() {
		logger.debug("Testing should find user bby email address");
		User expecteduser = repository.save(user1);

		User actualUser = repository.findByEmail("johnk@email.com");
		assertNotNull(actualUser);
		assertEquals(expecteduser.getId(), actualUser.getId());
		assertEquals(expecteduser.getFirstName(), actualUser.getFirstName());
		assertEquals(expecteduser.getLastName(), actualUser.getLastName());
		assertEquals(expecteduser.getUsername(), actualUser.getUsername());
		assertEquals(expecteduser.getPassword(), actualUser.getPassword());
	}

	/*
	 * Test to find user by email address
	 */
	@Test
	public void should_get_Null_when_user_ByEmail_not_found() {
		logger.debug("Testing should_handle_find_user_ByEmail_when_user_not_found");

		User actualUser = repository.findByEmail("sarahjackson@email.com");
		assertNull(actualUser);

	}

	/*
	 * Test to find user by email address
	 */
	@Test
	public void should_find_user_ByUsernamel() {
		logger.debug("Testing should find user bby email address");
		User expecteduser = repository.save(user1);

		User actualUser = repository.findByUsername("johnk1");
		assertNotNull(actualUser);
		assertEquals(expecteduser.getId(), actualUser.getId());
		assertEquals(expecteduser.getFirstName(), actualUser.getFirstName());
		assertEquals(expecteduser.getLastName(), actualUser.getLastName());
		assertEquals(expecteduser.getUsername(), actualUser.getUsername());
		assertEquals(expecteduser.getPassword(), actualUser.getPassword());
	}

	/*
	 * Test to find user by email address
	 */
	@Test
	public void should_get_Null_when_user_ByUsername_not_found() {
		logger.debug("Testing should_handle_find_user_ByEmail_when_user_not_found");

		User actualUser = repository.findByUsername("sarahjackson@email.com");
		assertNull(actualUser);

	}
}
