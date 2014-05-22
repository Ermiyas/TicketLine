package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class TopTenBarChartPane extends Pane {
	
	@SuppressWarnings("unchecked")
	public TopTenBarChartPane(){
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setPadding(new Insets(5, 0, 5, 0));
		grid.setMaxHeight(200d);
		
		ColumnConstraints column = new ColumnConstraints();
		/*column.setMinWidth(600);
		column.setPrefWidth(795d);
		column.setMaxWidth(795d);*/
		grid.getColumnConstraints().add(column);
		
		final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();
        final BarChart<Number,String> barChart = new BarChart<Number,String>(xAxis,yAxis);
        barChart.setLegendVisible(false);
        xAxis.setLabel("Verkaufte Tickets");
        yAxis.setLabel("Veranstaltung");
        
        XYChart.Series<Number,String> series = new XYChart.Series<Number,String>();      
        series.getData().add(new XYChart.Data<Number,String>(25601, "V1"));
        series.getData().add(new XYChart.Data<Number,String>(20148, "V2"));
        series.getData().add(new XYChart.Data<Number,String>(10000, "V3"));
        series.getData().add(new XYChart.Data<Number,String>(35407, "V4"));
        series.getData().add(new XYChart.Data<Number,String>(12000, "V5"));  
        
        barChart.getData().addAll(series);
        
        grid.add(barChart, 0, 0);
        this.getChildren().add(grid);
	}
}
