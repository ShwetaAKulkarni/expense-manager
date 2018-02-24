/**
 * 
 */
package aug.manas.expmgr.accountservice.util;

import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * @author shweta
 *
 */
public class ExpErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String errorMessage;

	public ExpErrorMessage() {

	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
