/**
 * 
 */
package aug.manas.expmgr.accountservice.util;

import java.io.Serializable;

/**
 * @author shweta
 *
 */
public class ExpAccountServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 8137349860282879062L;

	private String message;

	public String getMessage() {
		return message;
	}

	public ExpAccountServiceException() {

	}

	public ExpAccountServiceException(String message) {
		this.message = message;
	}

}
