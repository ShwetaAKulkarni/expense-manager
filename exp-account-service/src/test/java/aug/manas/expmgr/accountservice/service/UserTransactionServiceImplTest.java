/**
 * 
 */
package aug.manas.expmgr.accountservice.service;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import aug.manas.expmgr.accountservice.model.AccountTransaction;
import aug.manas.expmgr.accountservice.model.TransactionType;
import aug.manas.expmgr.accountservice.model.UserTransaction;
import aug.manas.expmgr.accountservice.repository.AccountTransactionRepository;
import aug.manas.expmgr.accountservice.repository.UserTransactionRepository;
import aug.manas.expmgr.accountservice.service.AccountTransactionService;
import aug.manas.expmgr.accountservice.service.UserTransactionService;
import aug.manas.expmgr.accountservice.service.impl.AccountTransactionServiceImpl;
import aug.manas.expmgr.accountservice.service.impl.UserTransactionServiceImpl;

/**
 * @author shweta
 *
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class UserTransactionServiceImplTest {

	private static final Logger logger = LoggerFactory.getLogger(UserTransactionServiceImplTest.class);

	@Mock
	private UserTransactionRepository userTransactionRepository;

	@Mock
	private AccountTransactionRepository accountTransactionRepository;

	@InjectMocks
	AccountTransactionService expTransactionService = new AccountTransactionServiceImpl(accountTransactionRepository);

	@InjectMocks
	private UserTransactionService userTransactionService = new UserTransactionServiceImpl(userTransactionRepository,
			expTransactionService);

	private AccountTransaction transaction1;
	private AccountTransaction transaction2;
	private AccountTransaction transaction3;
	private List<AccountTransaction> listOfTransactionsFromRepository;
	private UserTransaction expUserTransaction;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		/// setting up variables/data for use in tests
		Calendar cal = Calendar.getInstance();
		cal.set(2018, 0, 10);
		Date date1 = cal.getTime();

		cal.set(2018, 1, 11);
		Date date2 = cal.getTime();

		cal.set(2018, 1, 19);
		Date date3 = cal.getTime();

		transaction1 = new AccountTransaction("Salary", date1, TransactionType.INCOME, new BigDecimal(5000));
		transaction2 = new AccountTransaction("Rent", date2, TransactionType.EXPENSE, new BigDecimal(2000.00));
		transaction3 = new AccountTransaction("Cable", date3, TransactionType.EXPENSE, new BigDecimal(150.00));

		listOfTransactionsFromRepository = new ArrayList<>();

		listOfTransactionsFromRepository.add(transaction1);
		listOfTransactionsFromRepository.add(transaction2);
		listOfTransactionsFromRepository.add(transaction3);

		expUserTransaction = new UserTransaction(1L, transaction1.getId());

	}

	/*
	 * Test to get user transactions
	 */
	@Test
	public void test_get_listof_transactions_for_userId() {
		logger.debug("Testing get transactions for user");

		when(userTransactionRepository.findByUserId(anyLong())).thenReturn(Arrays.asList(1l, 2l, 3l));
		when(accountTransactionRepository.findByIdIn(anyListOf(Long.class)))
				.thenReturn(listOfTransactionsFromRepository);

		List<AccountTransaction> transactionsReturned = userTransactionService.getAllTransactionsforUser(1L);
		assertNotNull(transactionsReturned);
		assertEquals(listOfTransactionsFromRepository.size(), transactionsReturned.size());
		assertThat(transactionsReturned, containsInAnyOrder(listOfTransactionsFromRepository.get(0),
				listOfTransactionsFromRepository.get(1), listOfTransactionsFromRepository.get(2)));

	}

	/*
	 * Test to check if empty list is returned when no records are found for
	 * user
	 */

	@Test
	public void should_return_emptylist_when_no_record_for_userId() {
		logger.debug("Testing should_return_emptylist_when_no_record_for_userId");

		List<Long> emptylistOfTransactionsFromRepository = new ArrayList<>();
		when(userTransactionRepository.findByUserId(anyLong())).thenReturn(emptylistOfTransactionsFromRepository);
		List<AccountTransaction> transactionsReturned = userTransactionService.getAllTransactionsforUser(20L);
		assertNull(transactionsReturned);

	}

	/*
	 * Test to check if empty list is returned when no records are found for
	 * user
	 */

	@Test
	public void should_add_transaction_for_userId() {
		logger.debug("Testing should_add_transaction_for_user");

		AccountTransaction inputToTransactionSvc = transaction1;
		AccountTransaction outputTransaction = transaction1;

		outputTransaction.setId(10l);

		when(accountTransactionRepository.save(inputToTransactionSvc)).thenReturn(outputTransaction);
		when(userTransactionRepository.save(expUserTransaction)).thenReturn(expUserTransaction);

		AccountTransaction savedTransaction = userTransactionService.addTransaction(1L, inputToTransactionSvc);

		assertNotNull(savedTransaction);

		assertEquals(savedTransaction.getId(), outputTransaction.getId());
		assertEquals(savedTransaction.getDescription(), outputTransaction.getDescription());
		assertTrue(savedTransaction.getDate().compareTo(outputTransaction.getDate()) == 0);
		assertEquals(savedTransaction.getType(), outputTransaction.getType());
		assertEquals(savedTransaction.getAmount(), outputTransaction.getAmount());
	}

	/*
	 * Test to verify whether a transaction gets deleted
	 */
	@Test
	public void should_delete_transaction_by_transactionId() {
		logger.debug("Testing should_delete_transaction_by_transactionId");

		when(userTransactionRepository.findOne(anyLong())).thenReturn(null);

		boolean transactionDeleted = userTransactionService.deleteTransaction(2l);
		verify(accountTransactionRepository).delete(2l);
		verify(userTransactionRepository).delete(2l);
		assertTrue(transactionDeleted);
	}

	/*
	 * Test to verify whether a transaction that doesn't exist for deletion is
	 * handled
	 */
	@Test
	public void should_handle_delete_transaction_for_notexisting_transactionId() {
		logger.debug("Testing should_delete_transaction_by_transactionId");
		when(accountTransactionRepository.findOne(anyLong())).thenReturn(transaction1);
		boolean transactionDeleted = userTransactionService.deleteTransaction(2l);
		verify(accountTransactionRepository).delete(anyLong());
		assertFalse(transactionDeleted);
	}

	/*
	 * Test to verify whether a transaction is getting updated
	 */
	@Test
	public void should_update_transaction_for_a_user() {
		logger.debug("Testing should_update_transaction_for_a_user");

		AccountTransaction inputToTransactionSvc = transaction1;
		inputToTransactionSvc.setId(1l);

		AccountTransaction expectedOutputFromSvc = transaction2;
		expectedOutputFromSvc.setId(10l);

		when(accountTransactionRepository.save(inputToTransactionSvc)).thenReturn(expectedOutputFromSvc);

		AccountTransaction actualUpdatedTransaction = userTransactionService.updateTransaction(2L,
				inputToTransactionSvc);

		assertNotNull(actualUpdatedTransaction);
		assertEquals(expectedOutputFromSvc.getId(), actualUpdatedTransaction.getId());
		assertEquals("Rent", actualUpdatedTransaction.getDescription());
		assertTrue(expectedOutputFromSvc.getDate().compareTo(actualUpdatedTransaction.getDate()) == 0);
		assertEquals(TransactionType.EXPENSE, actualUpdatedTransaction.getType());
		assertEquals(expectedOutputFromSvc.getAmount(), actualUpdatedTransaction.getAmount());

	}
}
