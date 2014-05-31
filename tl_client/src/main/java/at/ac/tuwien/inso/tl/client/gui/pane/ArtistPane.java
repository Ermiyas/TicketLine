package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ArtistPane extends Pane {
	private String name;
	private String artistText;
	
	private Double textWidth = 350d;
	
	private Text txName;
	private Label lblText;
	
	public ArtistPane(String name, String artistText){
		this.name = name;
		this.artistText = artistText;
		
		init();
	}
	
	private void init(){
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(200);
		grid.getColumnConstraints().add(column);
		int row = 0;
		
		txName = new Text(name);
		txName.setWrappingWidth(textWidth);
		txName.setId("tx_title");
		grid.add(txName, 0, row++);
		
		lblText = new Label(artistText);
		lblText.setWrapText(true);
		lblText.setMaxWidth(textWidth);
		grid.add(lblText, 0, row++);
		
		grid.add(new Separator(), 0, row);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
