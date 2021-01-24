package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.pages.ReadingsPage;
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;


import java.util.List;

public class PlotView extends Application {

    private ReadingsPage current;
    public final LineChart<String, Number> chart;

    public PlotView() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        chart = new LineChart<>(xAxis, yAxis);
    }

    public void setCurrent(ReadingsPage current) {
        this.current = current;
        updateChart();
    }

    public void updateChart() {
        chart.getData().clear();
        if (current == null){
            return;
        }
        var list = current.getObservations();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(current.getKey());
        for(var observation: list) {
            series.getData().add(
                    new XYChart.Data<>(observation.getTime().toString(),
                    observation.getValue()));
        }
        chart.getData().add(series);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Data(
                new BasicParser(BasicParser::loadFromTestResources)
        );


        primaryStage.setTitle("Wykres 1");

        PlotView pv = new PlotView();
        pv.setCurrent(model.getReadingsPage(14, "PM10"));

        VBox vbox = new VBox(pv.chart);

        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}