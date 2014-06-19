package at.ac.tuwien.inso.tl.client.gui.pane;

import org.apache.log4j.Logger;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

public class SeatingPlanPane extends GridPane {
	private static final Logger LOG = Logger.getLogger(SeatingPlanPane.class);
	
	private Integer reservedSeats;
	private int numberOfColumns;
	
	public SeatingPlanPane() {
		this.setAlignment(Pos.CENTER);
		//this.setGridLinesVisible(true);
		
		ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setFillWidth(true);
        columnConstraints.setHgrow(Priority.ALWAYS);
        columnConstraints.setHalignment(HPos.CENTER);
		this.getColumnConstraints().add(columnConstraints);
		
		reservedSeats = 0;
		numberOfColumns = 0;
	}
	
	public void addRow(int row) {
		LOG.info("Row: " + row);
		RowConstraints rowConstraints = new RowConstraints();
		rowConstraints.setFillHeight(true);
		rowConstraints.setVgrow(Priority.ALWAYS);
		this.getRowConstraints().add(rowConstraints);
		
		Label lbl_row;
		lbl_row = new Label("Reihe " + row);
		this.add(lbl_row, 0, row);
	}
	
	public void addElement(int column, int row, SeatPane seatPane) {		
		if(column > numberOfColumns) {
			ColumnConstraints columnConstraints = new ColumnConstraints();
	        columnConstraints.setFillWidth(true);
	        columnConstraints.setHgrow(Priority.ALWAYS);
			this.getColumnConstraints().add(columnConstraints);
			numberOfColumns = column;
		}
						
		seatPane.setText(String.valueOf(column));
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
