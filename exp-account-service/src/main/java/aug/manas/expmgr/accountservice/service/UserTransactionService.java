/**
 * 
 */
package aug.manas.expmgr.accountservice.service;

import java.util.List;

import aug.manas.expmgr.accountservice.model.AccountTransaction;

/**
 * @author shweta
 *
 */
public interface UserTransactionService {

	AccountTransaction addTransaction(Long userId, AccountTransaction transaction);

	boolean deleteTransaction(Long transId);

	boolean deleteAllTransactions(Long userId);

	AccountTransaction updateTransaction(Long userId, AccountTransaction Long);

	List<AccountTransaction> getAllTransactionsforUser(Long userId);

	AccountTransaction findTransactionByTransactionId(Long transactionId);
}