package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SeatingPlanPane extends GridPane {
	
	private Integer reservedSeats;
	
	public SeatingPlanPane() {
		this.setAlignment(Pos.CENTER);
		this.getRowConstraints().add(new RowConstraints(40));
		this.getColumnConstraints().add(new ColumnConstraints(60));
		
		reservedSeats = 0;
	}
	
	public void addRow(int row) {
		this.getRowConstraints().add(new RowConstraints(40));
		Label lbl_row;
		lbl_row = new Label("Reihe " + row);
		this.add(lbl_row, 0, row);
	}
	
	public void addElement(int column, int row, SeatPane seatPane) {
		if(row == 1) {
			this.getColumnConstraints().add(new ColumnConstraints(60));
			Label lbl_seat;
			lbl_seat = new Label("Sitz " + column);
			GridPane.setHalignment(lbl_seat, HPos.CENTER);
			this.add(lbl_seat, column, 0);
		}
		
		
		GridPane.setHalignment(seatPane, HPos.CENTER);
		this.add(seatPane, column, row);
	}
	
	public void addReservation() {
		reservedSeats++;
	}
	
	public void undoReservation() {
		reservedSeats--;
	}
	
	public Integer getReservedSeats() {
		return reservedSeats;
	}
}
