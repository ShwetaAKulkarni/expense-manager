/**
 * 
 */
package aug.manas.expmgr.accountservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shweta
 *
 */
@Entity
@Table(name = "user_transaction")
public class UserTransaction implements Serializable {

	private static final long serialVersionUID = -302209030368856060L;

	@Column(name = "user_id")
	private Long userId;

	@Id
	@Column(name = "trans_id")
	private Long transId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTransId() {
		return transId;
	}

	public void setTransId(Long transId) {
		this.transId = transId;
	}

	protected UserTransaction() {

	}

	public UserTransaction(Long userId, Long transId) {
		this.userId = userId;
		this.transId = transId;
	}

}
