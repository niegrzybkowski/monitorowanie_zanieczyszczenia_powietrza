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

import java.util.Collections;

public class PlotView {

    private ReadingsPage current;
    private final boolean isXaxis;


    private LineChart<String, Number> chart;

    public PlotView(boolean isXAxisActive) {
        chart = makeNewChart();
        this.isXaxis = isXAxisActive;
    }

    public LineChart<String, Number> makeNewChart() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setTickMarkVisible(false);
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickMarkVisible(false);
        return new LineChart<>(xAxis, yAxis);
    }

    public LineChart<String, Number> getChart() {
        return chart;
    }

    public void setCurrent(ReadingsPage current) {
        this.current = current;
        updateChart();
    }

    public void updateChart() {
        chart = makeNewChart();
        chart.setCreateSymbols(false);
        if (current == null){
//            System.out.println("current == null");
            chart.setVisible(false);
            return;
        }
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(current.getKey());
        var list = current.getObservations();

        for(int i = list.size(); i-- > 0;) {
            var data = new XYChart.Data<String, Number>(
                    list.get(i).getTime().toString(),
                    list.get(i).getValue());
            series.getData().add(data);
        }
        chart.getData().add(series);

        if(!isXaxis) {
            chart.getXAxis().setOpacity(0);
            chart.getXAxis().setTickLabelsVisible(false);
        }

    }

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Model model = new Data(
//                new BasicParser(BasicParser::loadFromTestResources)
//        );
//
//
//        primaryStage.setTitle("Wykres 1");
//
//        PlotView pv = new PlotView(false);
//        pv.setCurrent(model.getReadingsPage(14, "PM10"));
//
//        VBox vbox = new VBox(pv.chart);
//
//        Scene scene = new Scene(vbox, 400, 200);
//
//        primaryStage.setScene(scene);
//        primaryStage.setHeight(300);
//        primaryStage.setWidth(1200);
//
//        primaryStage.show();
//    }

    public ReadingsPage getCurrent() {
        return current;
    }
}