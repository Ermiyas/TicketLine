package at.ac.tuwien.inso.tl.client.util;

import java.util.Set;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

public class ValidationEventHandler<T> implements EventHandler<KeyEvent> {

	private Class<T> beanClass;
	private String propertyName;
	private Validator validator;

	public ValidationEventHandler(Class<T> beanClass, String propertyName,
			Validator validator) {
		this.beanClass = beanClass;
		this.propertyName = propertyName;
		this.validator = validator;
	}

	@Override
	public void handle(KeyEvent event) {
		String text = null;
		if(((TextInputControl) event.getTarget()).getText() == null) {
			text = event.getCharacter();
		} else {
			text = ((TextInputControl) event.getTarget()).getText() + event.getCharacter();
		}
		Set<ConstraintViolation<T>> violations = validator.validateValue(
				beanClass, propertyName, text);

		if (!violations.isEmpty()) {
			((TextInputControl) event.getTarget()).setStyle("-fx-border-color: red");
			((TextInputControl) event.getTarget()).setTooltip(new Tooltip(violations.iterator().next().getMessage()));
		} else {
			((Node) event.getTarget()).setStyle("-fx-border-color: null");
			((TextInputControl) event.getTarget()).setTooltip(null);
			
		}
	}
}