package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class TopTenBarChartPane extends GridPane {
	
	@SuppressWarnings("unchecked")
	public TopTenBarChartPane(){
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(5, 0, 5, 0));
		this.setMaxHeight(200d);
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(805);
		this.getColumnConstraints().add(column);
		
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
        barChart.setLegendVisible(false);
        //xAxis.setLabel("Veranstaltung");
        //yAxis.setLabel("Verkaufte Tickets");
        
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();      
        series.getData().add(new XYChart.Data<String, Number>("#1", 35407));
        series.getData().add(new XYChart.Data<String, Number>("#2", 32809));
        series.getData().add(new XYChart.Data<String, Number>("#3", 31032));
        series.getData().add(new XYChart.Data<String, Number>("#4", 27914));
        series.getData().add(new XYChart.Data<String, Number>("#5", 27469));
        series.getData().add(new XYChart.Data<String, Number>("#6", 22103));
        series.getData().add(new XYChart.Data<String, Number>("#7", 20148));
        series.getData().add(new XYChart.Data<String, Number>("#8", 17013));
        series.getData().add(new XYChart.Data<String, Number>("#9", 12000));
        series.getData().add(new XYChart.Data<String, Number>("#10", 10258));  
        
        barChart.getData().addAll(series);
        
        this.add(barChart, 0, 0);
	}
}
