package at.ac.tuwien.inso.tl.client.exception;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.inso.tl.dto.FieldError;

public class ValidationException extends ServiceException {

	private static final long serialVersionUID = 280721989480328252L;
	
	private List<FieldError> fieldErrors = new ArrayList<FieldError>();

	public ValidationException() {
		super();
	}
	
	public ValidationException(String message) {
		super(message);
	}
	
	public ValidationException(Throwable throwable) {
		super(throwable);
	}
	
	public ValidationException(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
	
	public ValidationException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public ValidationException(String message, List<FieldError> fieldErrors) {
		super(message);
		this.fieldErrors = fieldErrors;
	}
	
	public List<FieldError> getFieldErrors() {
		return this.fieldErrors;
	}
}
