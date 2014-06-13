package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ArtistPane extends Pane {
	private Integer id;
	private String firstname;
	private String lastname;
	
	private Double textWidth = 700d;
	
	private Text txName;
	
	public ArtistPane(Integer id, String firstname, String lastname){
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		
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
		
		txName = new Text(firstname + " " + lastname);
		txName.setWrappingWidth(textWidth);
		txName.setId("tx_title");
		grid.add(txName, 0, row++);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
	
	public Integer getArtistId() {
		return id;
	}
	
	public String getArtistFirstname() {
		return firstname;
	}
	
	public String getArtistLastname() {
		return lastname;
	}
}
