package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PerformancePane extends Pane {
	private String title;
	private String street;
	private String location;
	private String country;
	private int postal;
	private String description;
	
	private Double textWidth = 500d;
	
	private Text tx_title;
	private Label lbl_details;
	private Label lbl_text;
	
	public PerformancePane(String title, String street, String location, 
						   String country, int postal, String description) {
		this.title = title;
		this.street = street;
		this.location = location;
		this.country = country;
		this.postal = postal;
		this.description = description;
		
		init();
	}
	
	private void init() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(300);
		grid.getColumnConstraints().add(column);
		int row = 0;
		
		tx_title = new Text(title);
		tx_title.setWrappingWidth(textWidth);
		tx_title.setId("tx_title");
		grid.add(tx_title, 0, row++);
		
		lbl_details = new Label(location + ", " + street + ", " + postal + " " + country);
		lbl_details.setWrapText(true);
		lbl_details.setMaxWidth(textWidth);
		grid.add(lbl_details, 0, row++);
		
		lbl_text = new Label(description);
		lbl_text.setWrapText(true);
		lbl_text.setMaxWidth(textWidth);
		grid.add(lbl_text, 0, row++);
		
		grid.add(new Separator(), 0, row);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
