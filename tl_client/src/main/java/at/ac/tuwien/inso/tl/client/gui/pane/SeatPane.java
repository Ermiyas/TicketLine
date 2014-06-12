package at.ac.tuwien.inso.tl.client.gui.pane;

import org.apache.log4j.Logger;

import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class SeatPane extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SeatPane.class);
	
	private Integer performanceId;
	private Integer seatId;
	
	public SeatPane(Integer performanceId, Integer seatId) {
		this.performanceId = performanceId;
		this.seatId = seatId;
		
		init();
	}
	
	public SeatPane(Integer performanceId, Integer seatId, boolean reserved) {
		
	}
	
	private void init() {
		setStyle("-fx-background-color: #b6e7c9;");
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					setEffect(new DropShadow());
				}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					setEffect(null);
				}
		});
			
		this.addEventHandler(MouseEvent.MOUSE_CLICKED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					/*int column = GridPane.getColumnIndex(button);
					int row = GridPane.getRowIndex(button);*/
					LOG.info("Sitz mit Id: " + seatId + " wurde für die Aufführung: " + performanceId + " reserviert.");
					if(isSelected()) {
						setStyle("-fx-background-color: #f75555;");
					} else {
						setStyle("-fx-background-color: #b6e7c9;");
					}
				}			
		});
	}
}
