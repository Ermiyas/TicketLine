package at.ac.tuwien.inso.tl.dto;

import javax.validation.constraints.NotNull;

public class FieldError {
	@NotNull
	private String field;

	@NotNull
    private String message;

	public FieldError() {
	}
	
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }

	public String getField() {
		return field;
	}

	public String getMessage() {
		return message;
	}
}
