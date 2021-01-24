package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.pages.ReadingsPage;
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;

public class OknoWykres {

    public static void popUp(ReadingsPage readingsPage){
        Stage stage = new Stage();

        stage.setTitle("Wykres 1");

        PlotView pv = new PlotView();
        pv.setCurrent(readingsPage);
        pv.updateChart();

        VBox vbox = new VBox(pv.getChart());

        Scene scene = new Scene(vbox, 400, 200);

        stage.setScene(scene);
        stage.setHeight(300);
        stage.setWidth(1200);

        stage.show();
    }
}
