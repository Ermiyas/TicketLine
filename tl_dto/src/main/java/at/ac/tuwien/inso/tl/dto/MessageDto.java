package at.ac.tuwien.inso.tl.dto;

import java.util.ArrayList;
import java.util.List;

public class MessageDto {
	private MessageType type;
	
	private String text;
	
	private List<FieldError> fieldErrors = new ArrayList<>();
	
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean hasFieldErrors() {
		if ((this.fieldErrors != null) && (this.fieldErrors.size() > 0)) {
			return true;
		} else {
			return false;
		}
	}

    public void addFieldError(String path, String message) {
        FieldError error = new FieldError(path, message);
        fieldErrors.add(error);
    }
    
    public List<FieldError> getFieldErrors(){
    	return fieldErrors;
    }
}
