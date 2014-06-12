package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class NewsPane extends Pane{
	private String title;
	private String date;
	private String newsText;
	
	private Double textWidth = 350d;
	
	private Text tx_title;
	private Text tx_date;
	private Label lbl_text;
	
	public NewsPane(String title, String date, String newsText){
		this.title = title;
		this.date = date;
		this.newsText = newsText;
		
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

		tx_date = new Text(date);
		tx_date.setId("tx_date");
		grid.add(tx_date, 0, row++);
		
		tx_title = new Text(title);
		tx_title.setWrappingWidth(textWidth);
		tx_title.setId("tx_title");
		grid.add(tx_title, 0, row++);
		
		lbl_text = new Label(newsText);
		lbl_text.setWrapText(true);
		lbl_text.setMaxWidth(textWidth);
		grid.add(lbl_text, 0, row++);
		
		grid.add(new Separator(), 0, row);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
