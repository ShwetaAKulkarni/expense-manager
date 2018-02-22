/**
 * 
 */
package aug.manas.expmgr.accountservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aug.manas.expmgr.accountservice.model.AccountTransaction;
import aug.manas.expmgr.accountservice.model.TransactionType;

/**
 * @author shweta
 *
 */
public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

	List<AccountTransaction> findByType(TransactionType type);

	@Query("SELECT trans FROM AccountTransaction trans WHERE trans.date BETWEEN :fromDate AND :toDate ")
	List<AccountTransaction> findByDate(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

	List<AccountTransaction> findByIdIn(List<Long> idList);

	void deleteByIdIn(List<Long> idList);
	
//	List<ExpTransaction> findFist10ByDateOrderByDateDesc();

}
