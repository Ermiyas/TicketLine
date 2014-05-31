package at.ac.tuwien.inso.tl.client.gui.pane;

import at.ac.tuwien.inso.tl.client.util.BundleManager;

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
	private String date;
	private String time;
	private Integer price;
	private String description;
	
	private Double textWidth = 645d;
	
	private Text tx_title;
	private Label lbl_details;
	private Label lbl_price;
	private Label lbl_text;
	
	public PerformancePane(String title, String date, String time, 
						   Integer price, String description) {
		this.title = title;
		this.date = date;
		this.time = time;
		this.price = price;
		this.description = description;
		
		init();
	}
	
	private void init() {
		GridPane grid = new GridPane();
		
		GridPane gridleft = new GridPane();
		gridleft.setAlignment(Pos.CENTER_LEFT);
		gridleft.setHgap(5);
		gridleft.setVgap(5);
		gridleft.setPadding(new Insets(25, 25, 25, 25));
		
		GridPane gridright = new GridPane();
		gridright.setAlignment(Pos.CENTER);
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(300);
		grid.getColumnConstraints().add(column);
		gridleft.getColumnConstraints().add(column);
		column.setMinWidth(100);
		grid.getColumnConstraints().add(column);
		gridright.getColumnConstraints().add(column);
		int row = 0;
		
		tx_title = new Text(title);
		tx_title.setWrappingWidth(textWidth);
		tx_title.setId("tx_title");
		gridleft.add(tx_title, 0, row++);
		
		lbl_details = new Label(BundleManager.getBundle().getString("searchpage.performance.date") + " " + date + ", "
							  + BundleManager.getBundle().getString("searchpage.performance.time") + " " + time);
		lbl_details.setWrapText(true);
		lbl_details.setMaxWidth(textWidth);
		gridleft.add(lbl_details, 0, row++);
		
		gridleft.add(new Separator(), 0, row++);
		
		lbl_text = new Label(description);
		lbl_text.setWrapText(true);
		lbl_text.setMaxWidth(textWidth);
		gridleft.add(lbl_text, 0, row);
		
		String priceString = String.valueOf(price);
		lbl_price = new Label("€ " + priceString.substring(0, priceString.length()-2) + "." + priceString.substring(priceString.length()-2));
		lbl_price.setWrapText(true);
		lbl_price.setMaxWidth(200);
		lbl_price.setAlignment(Pos.CENTER);
		gridright.add(lbl_price, 0, 0);
		
		grid.add(gridleft, 0, 0);
		grid.add(gridright, 1, 0);
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
