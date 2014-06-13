package at.ac.tuwien.inso.tl.client.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

/**
 * Eine Bool-Zelle für den Warenkorb, welche durch eine Checkbox repräsentiert wird
 */
public class BooleanCell extends TableCell<BasketEntryContainer, Boolean> {
    private CheckBox checkBox;
    public BooleanCell() {
        checkBox = new CheckBox();
        checkBox.setDisable(false);
        checkBox.selectedProperty().addListener(new ChangeListener<Boolean> () {
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(isEditing())
                    commitEdit(newValue);
            }
        });
        this.setGraphic(checkBox);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        this.setEditable(true);
        this.setAlignment(Pos.CENTER);
    }
    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.setDisable(false);
        checkBox.requestFocus();
    }
    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setDisable(true);
    }
    public void commitEdit(Boolean value) {
        super.commitEdit(value == null ? false : value);
        if(value == null) {
        	checkBox.setDisable(true);
        } else {
        	checkBox.setDisable(false);
        }
    }
    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (!isEmpty()) {
            checkBox.setSelected(item);
        }
    }
}