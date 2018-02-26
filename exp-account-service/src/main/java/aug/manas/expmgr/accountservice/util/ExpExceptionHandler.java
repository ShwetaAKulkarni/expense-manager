/**
 * 
 */
package aug.manas.expmgr.accountservice.util;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author shweta
 *
 */
@ControllerAdvice
public class ExpExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ExpAccountServiceException.class)
	public ResponseEntity<ExpErrorMessage> exceptionAccountServiceHandler(Exception ex) {
		ExpErrorMessage error = new ExpErrorMessage();
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		error.setTimestamp(new Date());
		System.out.println(ex.getMessage());
		error.setErrorMessage(ex.getMessage());
		return new ResponseEntity<ExpErrorMessage>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExpErrorMessage> exceptionHandler(Exception ex) {
		ExpErrorMessage error = new ExpErrorMessage();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setTimestamp(new Date());
		error.setErrorMessage("The request could not be understood by the server due to malformed syntax.");
		return new ResponseEntity<ExpErrorMessage>(error, HttpStatus.BAD_REQUEST);
	}

}
