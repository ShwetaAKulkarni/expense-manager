/**
 * 
 */
package aug.manas.expmgr.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aug.manas.expmgr.accountservice.model.UserTransaction;

/**
 * @author shweta
 *
 */
public interface UserTransactionRepository extends JpaRepository<UserTransaction, Long> {

	List<UserTransaction> findByTransId(Long transId);

	@Query(value = " SELECT  ut.transId FROM UserTransaction AS ut WHERE ut.userId = :userId")
	List<Long> findByUserId(@Param("userId") Long userId);

	void deleteByUserId(@Param("userId") Long userId);
}
