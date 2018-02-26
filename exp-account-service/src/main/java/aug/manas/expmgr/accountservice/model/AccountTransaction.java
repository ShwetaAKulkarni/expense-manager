/**
 * 
 */
package aug.manas.expmgr.accountservice.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author shweta
 *
 */
@Entity
@Table(name = "account_transaction")
public class AccountTransaction implements Serializable {

	private static final long serialVersionUID = -302209030368856060L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "trans_Id")
	private Long id;

	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType type;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
	@Temporal(TemporalType.DATE)
	private Date date;
	private BigDecimal amount;

	protected AccountTransaction() {

	}

	public AccountTransaction(String description, Date date, TransactionType type, BigDecimal amount) {
		this.description = description;
		this.date = date;
		this.type = type;
		this.amount = amount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "AccountTransaction:: TransactionId: " + this.getId() + " Description: " + this.getDescription()
				+ " TransactionType: " + this.getType() + "TransactionDate: " + this.getDate() + " Amount: "
				+ this.getAmount();

	}
}
