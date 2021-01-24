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
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;

public class OknoWykres extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        Model model = new Data(
                new BasicParser(BasicParser::loadFromTestResources)
        );


        primaryStage.setTitle("Wykres 1");

        PlotView pv = new PlotView();
        pv.setCurrent(model.getReadingsPage(currentStation, key));

        VBox vbox = new VBox(pv.getChart());



        VBox vbox = new VBox(pv.getChart());


        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setWidth(1200);

        primaryStage.show();
    }

    public static void run(){
        launch();
    }
}
