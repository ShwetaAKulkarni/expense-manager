/**
 * 
 */
package aug.manas.expmgr.accountservice.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aug.manas.expmgr.accountservice.model.AccountTransaction;
import aug.manas.expmgr.accountservice.repository.AccountTransactionRepository;
import aug.manas.expmgr.accountservice.service.AccountTransactionService;

/**
 * @author shweta
 *
 */
@Service("accountTransactionService")
public class AccountTransactionServiceImpl implements AccountTransactionService {
	private static final Logger logger = LoggerFactory.getLogger(AccountTransactionServiceImpl.class);

	/**
	 * @param accountTransactionRepository
	 */
	@Autowired
	public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository) {
		super();
		this.accountTransactionRepository = accountTransactionRepository;
	}

	private AccountTransactionRepository accountTransactionRepository;

	@Override
	public AccountTransaction addTransaction(AccountTransaction expTransaction) {
		logger.debug("Adding transaction");
		AccountTransaction savedTransaction = accountTransactionRepository.save(expTransaction);
		if (savedTransaction == null) {
			return null;
		}
		logger.debug("Transaction added for transactionId " + savedTransaction.getId());
		return savedTransaction;

	}

	@Override
	public boolean deleteTransaction(Long transactionId) {
		boolean result = false;
		logger.debug("Deleting transaction for transaction Id" + transactionId);
		if (transactionId == null || transactionId <= 0) {
			logger.error("Transaction cannot deleted for transactionId " + transactionId);

		} else {
			accountTransactionRepository.delete(transactionId);
			if (accountTransactionRepository.findOne(transactionId) == null) {
				logger.debug("Transaction with transactionId " + transactionId + " was deleted successfully");
				result = true;
			}

		}

		return result;

	}

	@Override
	public AccountTransaction updateTransaction(AccountTransaction expTransaction) {
		logger.debug("Updating transactions");
		if (expTransaction != null && expTransaction.getId() >= 1) {
			logger.debug("Updating transaction for transaction Id " + expTransaction.getId());
			expTransaction = accountTransactionRepository.save(expTransaction);
		}
		return expTransaction;

	}

	@Override
	public AccountTransaction getTransactionById(Long transactionId) {
		logger.debug("Getting transaction for Id " + transactionId);
		AccountTransaction accountTransactionById = null;
		if (transactionId != null && transactionId >= 1) {

			accountTransactionById = accountTransactionRepository.findOne(transactionId);
		}
		return accountTransactionById;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see aug.manas.accountservice.service.AccountTransactionService#
	 * getTransactionsListByIds(java.util.List)
	 */
	@Override
	public List<AccountTransaction> getTransactionsListByIds(List<Long> idList) {
		logger.debug("Getting transaction list for Ids in list ");
		List<AccountTransaction> accountTransactionList = null;
		if (idList != null && !idList.isEmpty()) {
			accountTransactionList = accountTransactionRepository.findByIdIn(idList);
		}
		return accountTransactionList;
	}

	/**
	 * Method to delete all transactions by transaction Ids in list
	 */
	@Override
	public boolean deleteTransactionsListByIds(List<Long> idList) {
		logger.debug("Deleting transaction list for Ids in list " + Arrays.asList(idList));
		boolean result = false;
		if (idList != null && !idList.isEmpty()) {
			accountTransactionRepository.deleteByIdIn(idList);

			// validating whether the transactions in list are deleted
			List<AccountTransaction> accountTransactionList = getTransactionsListByIds(idList);
			if (accountTransactionList == null || accountTransactionList.isEmpty()) {
				logger.info("Transactions deleted successfully for Ids in list ");
				result = true;
			} else {
				logger.error("Transactions not deleted for IDs " + Arrays.asList(idList));
			}

		} else {
			logger.info("List is either null or empty");
		}
		return result;
	}

}
