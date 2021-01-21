package pl.mini.pw.zanieczyszczenie.org.ui.map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Demo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();
        mapView.addPOI(52.23, 21.01, Color.BLUE, e -> System.out.println("hello"));
        var pane = mapView.getPane();
        VBox root = new VBox(pane);
        stage.setScene(new Scene(root));
        stage.setTitle("Pluto explorer");
        stage.show();
    }

    public static void run(){
        launch();
    }
}
