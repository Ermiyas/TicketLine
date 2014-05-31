package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ArtistPane extends Pane {
	private String firstName;
	private String lastName;
	
	private Double textWidth = 700d;
	
	private Text txName;
	
	public ArtistPane(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
		
		init();
	}
	
	private void init(){
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(300);
		grid.getColumnConstraints().add(column);
		int row = 0;
		
		txName = new Text(firstName + " " + lastName);
		txName.setWrappingWidth(textWidth);
		txName.setId("tx_title");
		grid.add(txName, 0, row++);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
