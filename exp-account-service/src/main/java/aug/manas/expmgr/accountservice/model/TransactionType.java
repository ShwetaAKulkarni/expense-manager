/**
 * 
 */
package aug.manas.expmgr.accountservice.model;

/**
 * @author shweta
 *
 */
public enum TransactionType {
	INCOME, EXPENSE;

	public static TransactionType getDefault() {
		return EXPENSE;
	}

}