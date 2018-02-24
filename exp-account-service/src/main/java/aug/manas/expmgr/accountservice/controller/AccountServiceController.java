/**
 * 
 */
package aug.manas.expmgr.accountservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import aug.manas.expmgr.accountservice.model.AccountTransaction;
import aug.manas.expmgr.accountservice.service.UserTransactionService;
import aug.manas.expmgr.accountservice.util.ExpAccountServiceException;

/**
 * @author shweta
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/api/acct-service")
public class AccountServiceController {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceController.class);

	@Autowired
	private UserTransactionService userTransactionService;

	/**
	 * List All transactions for user. UserId is path parameter
	 * 
	 * @param userId
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}/transaction", method = RequestMethod.GET)
	public ResponseEntity<List<AccountTransaction>> listAllTransactionForUser(@PathVariable("userId") long userId)
			throws ExpAccountServiceException {
		logger.debug("Getting list of all transaction for user id " + userId);
		List<AccountTransaction> accountTransactionlist = null;
		if (userId > 0) {
			accountTransactionlist = userTransactionService.getAllTransactionsforUser(userId);
			if (accountTransactionlist == null || accountTransactionlist.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		} else {
			logger.error("User Id is not valid " + userId);
			throw new ExpAccountServiceException("User Id is not valid " + userId);
		}
		return new ResponseEntity<List<AccountTransaction>>(accountTransactionlist, HttpStatus.OK);

	}

	/**
	 * Get transaction for User userId and transactionId are path params
	 * 
	 * @param userId
	 * @param transactionId
	 * @return
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}/transaction/{transactionId}", method = RequestMethod.GET)
	public ResponseEntity<AccountTransaction> TransactionForUser(@PathVariable("userId") long userId,
			@PathVariable("transactionId") long transactionId) throws ExpAccountServiceException {
		logger.debug("Getting list of all transaction for user id " + userId);
		AccountTransaction accountTransaction = null;
		if (userId > 0 && transactionId > 0) {
			accountTransaction = userTransactionService.findTransactionByTransactionId(transactionId);
			if (accountTransaction == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
		} else {
			logger.error("Either userId {} or transactionId  {} is not valid ", userId, transactionId);
			throw new ExpAccountServiceException(
					"Either userId " + userId + " or transactionId " + transactionId + " is not valid ");
		}
		return new ResponseEntity<AccountTransaction>(accountTransaction, HttpStatus.OK);

	}

	/**
	 * addTransaction for User userId is path param and Transaction is query
	 * param in post.
	 * 
	 * @param userId
	 * @param transaction
	 * @param ucBuilder
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}/transaction", method = RequestMethod.POST)
	public ResponseEntity<AccountTransaction> addTransactionForUser(@PathVariable("userId") long userId,
			@Valid @RequestBody AccountTransaction transaction, UriComponentsBuilder ucBuilder)
			throws ExpAccountServiceException {
		logger.debug("Adding Transaction for User id " + userId);
		ResponseEntity<AccountTransaction> response = null;
		if (userId > 0) {
			AccountTransaction accountTransactionCreated = userTransactionService.addTransaction(userId, transaction);
			if (accountTransactionCreated != null) {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.setLocation(ucBuilder.path("/api/transactions/{userId}/transaction/{id}")
						.buildAndExpand(userId, accountTransactionCreated.getId()).toUri());
				response = new ResponseEntity<AccountTransaction>(accountTransactionCreated, httpHeaders,
						HttpStatus.CREATED);
				logger.info("Transaction added successfully for User id " + userId);
			} else {
				logger.error("Transaction is not added for user id" + userId);
				throw new ExpAccountServiceException("Unable to add transaction for userId." + userId);
			}
		} else {
			logger.error("Transaction cannot be added. Invalid user id" + userId);
		}

		return response;
	}

	/**
	 * updateTransaction for User userId and transactionId are path params and
	 * Transaction is query param in put.
	 * 
	 * @param userId
	 * @param transaction
	 * @return
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}/transaction/{transactionId}", method = RequestMethod.PUT)
	public ResponseEntity<AccountTransaction> updateTransactionForUser(@PathVariable("userId") long userId,
			@PathVariable("transactionId") long transactionId, @Valid @RequestBody AccountTransaction transaction)
			throws ExpAccountServiceException {
		logger.debug("Updating Transaction " + transactionId + " for User id " + userId);
		ResponseEntity<AccountTransaction> response = null;
		if (userId > 0 && transactionId > 0) {
			AccountTransaction accountTransaction = userTransactionService
					.findTransactionByTransactionId(transactionId);

			if (accountTransaction == null) {
				logger.error("Unable to update. Transaction with id {} not found.", transactionId);
				throw new ExpAccountServiceException("Unable to upate. Transaction with id not found." + transactionId);

			}
			accountTransaction.setDescription(transaction.getDescription());
			accountTransaction.setDate(transaction.getDate());
			accountTransaction.setType(transaction.getType());
			accountTransaction.setAmount(transaction.getAmount());

			AccountTransaction accountTransactionUpdated = userTransactionService.updateTransaction(userId,
					accountTransaction);
			if (accountTransactionUpdated != null) {
				response = new ResponseEntity<AccountTransaction>(accountTransactionUpdated, HttpStatus.OK);
				logger.info("Transaction with Id " + transactionId + " updated successfully  for User id " + userId);
			}
		} else {
			logger.error("Transaction with id " + transactionId + " cannot be updated for UserId " + userId
					+ ". Invalid parameters");
			throw new ExpAccountServiceException("Transaction with id " + transactionId
					+ " cannot be updated for UserId " + userId + ". Invalid parameters");
		}

		return response;
	}

	/**
	 * * deleteTransaction for User . userId and transactionId are path params
	 * 
	 * @param userId
	 * @param transactionId
	 * @return
	 * @throws ExpAccountServiceException
	 */
	@RequestMapping(value = "/{userId}/transaction/{transactionId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteTransactionForUser(@PathVariable("userId") long userId,
			@PathVariable("transactionId") long transactionId) throws ExpAccountServiceException {
		logger.info("Deleting Transaction " + transactionId + " for User id " + userId);
		ResponseEntity<AccountTransaction> response = null;
		if (userId > 0 && transactionId > 0) {
			boolean transactionDeleted = userTransactionService.deleteTransaction(transactionId);

			if (transactionDeleted) {
				logger.info("Transaction with id " + transactionId + "deleted successfully for User id " + userId);
				response.status(HttpStatus.OK);
			} else {
				logger.error("Unable to delete transactions for userId." + userId);
				throw new ExpAccountServiceException("Unable to delete transactions for userId " + userId);
			}

		} else {
			logger.error("Transaction " + transactionId + " cannot be deleted for UserId " + userId
					+ ". Invalid parameters");
		}

		return response;
	}

	@RequestMapping(value = "/{userId}/transaction", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteAllTransactionsForUser(@PathVariable("userId") long userId)
			throws ExpAccountServiceException {
		logger.info("Deleting all transactions for User id " + userId);
		ResponseEntity<AccountTransaction> response = null;
		if (userId > 0) {
			boolean transactionDeleted = userTransactionService.deleteAllTransactions(userId);

			if (transactionDeleted) {
				response.status(HttpStatus.OK);
			} else {
				logger.error("Unable to delete transactions for userId." + userId);
				throw new ExpAccountServiceException("Unable to delete transactions for userId " + userId);
			}

		} else {
			logger.error("Transactions cannot be deleted. Invalid user id" + userId);
		}

		return response;
	}

}
