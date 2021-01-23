package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.pages.ReadingsPage;
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlotView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model model = new Data(
                new BasicParser(BasicParser::loadFromTestResources)
        );

        System.out.println(model.getReadingsPage(14, "PM10"));


        primaryStage.setTitle("Wykres 1");

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Data");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Stężenie pyłu");

        LineChart lineChart = new LineChart(xAxis, yAxis);

        XYChart.Series dataSeries = new XYChart.Series();
        dataSeries.setName("st");

        List<ReadingsPage.Observation> list = model.getReadingsPage(14, "PM10").getObservations();
        int i = 1;
        for(ReadingsPage.Observation ob : list){
            dataSeries.getData().add(new XYChart.Data(ob.getTime().toString(), ob.getValue()));
            i++;
        }

        lineChart.getData().add(dataSeries);

        VBox vbox = new VBox(lineChart);

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