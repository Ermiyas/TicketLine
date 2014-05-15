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
	
	private Double textWidth = 350d;
	
	private Text txTitle;
	private Label lblText;
	
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
		column.setMinWidth(200);
		grid.getColumnConstraints().add(column);
		int row = 0;
		
		txTitle = new Text(title);
		txTitle.setWrappingWidth(textWidth);
		txTitle.setId("tx_title");
		grid.add(txTitle, 0, row++);
		
		lblText = new Label(description);
		lblText.setWrapText(true);
		lblText.setMaxWidth(textWidth);
		grid.add(lblText, 0, row++);
		
		grid.add(new Separator(), 0, row);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
