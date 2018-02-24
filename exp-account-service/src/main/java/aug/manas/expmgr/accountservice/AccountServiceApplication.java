package aug.manas.expmgr.accountservice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import aug.manas.expmgr.accountservice.configuration.JpaConfiguration;
import aug.manas.expmgr.accountservice.model.AccountTransaction;
import aug.manas.expmgr.accountservice.model.TransactionType;
import aug.manas.expmgr.accountservice.model.User;
import aug.manas.expmgr.accountservice.repository.UserRepository;
import aug.manas.expmgr.accountservice.service.UserTransactionService;

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = { "aug.manas.expmgr.accountservice" })
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Profile("test")
	@Bean
	public CommandLineRunner setup(UserRepository userRepository, UserTransactionService userTransactionService) {
		return (args) -> {
			// Add user 1
			
			Calendar cal = Calendar.getInstance();
			cal.set(2018, 0, 10);
			Date date1 = cal.getTime();

			cal.set(2018, 1, 11);
			Date date2 = cal.getTime();

			cal.set(2018, 1, 19);
			Date date3 = cal.getTime();
			
			AccountTransaction transaction1 = new AccountTransaction("Mobile", date1, TransactionType.EXPENSE,
					new BigDecimal(100.00));
			AccountTransaction transaction2 = new AccountTransaction("Rent", date2, TransactionType.EXPENSE,
					new BigDecimal(2000.00));
			AccountTransaction transaction3 = new AccountTransaction("Cable", date3, TransactionType.EXPENSE,
					new BigDecimal(150.00));
			AccountTransaction transaction4 = new AccountTransaction("Salary",date3, TransactionType.INCOME,
					new BigDecimal(5000.00));
			
			List<AccountTransaction> expt1 = new ArrayList<>();
			expt1.add(transaction1);
			expt1.add(transaction2);
			expt1.add(transaction3);
			expt1.add(transaction4);
			userRepository.save(new User("Shweta", "Kulkarni", "Shwetak", "12342354", "shwetak@noemail.com"));
			userTransactionService.addTransaction(1L, transaction1);
			userTransactionService.addTransaction(1L, transaction2);
			userTransactionService.addTransaction(1L, transaction3);
			userTransactionService.addTransaction(1L, transaction4);

			// Add user 2
			
			cal.set(2017, 11, 11);
			Date date4 = cal.getTime();

			cal.set(2017, 11, 19);
			Date date5 = cal.getTime();
			
			AccountTransaction transaction5 = new AccountTransaction("InsuranceClaim", date1, TransactionType.INCOME,
					new BigDecimal(5000.00));
			AccountTransaction transaction6 = new AccountTransaction("Auto", date4, TransactionType.EXPENSE,
					new BigDecimal(200.00));
			AccountTransaction transaction7 = new AccountTransaction("Salary", date5, TransactionType.INCOME,
					new BigDecimal(8000.00));
			List<AccountTransaction> expt2 = new ArrayList<>();
			expt2.add(transaction5);
			expt2.add(transaction6);
			expt2.add(transaction7);
			userRepository.save(new User("John", "Doe", "johndoe12", "sdadsf@df", "johndoe@noemail.com"));
			userTransactionService.addTransaction(2L, transaction5);
			userTransactionService.addTransaction(2L, transaction6);
			userTransactionService.addTransaction(2L, transaction7);

		};

	}
}
