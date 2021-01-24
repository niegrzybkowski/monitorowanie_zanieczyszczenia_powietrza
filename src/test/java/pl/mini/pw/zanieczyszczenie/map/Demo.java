package pl.mini.pw.zanieczyszczenie.map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.communicator.TestUtilities;
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;
import pl.mini.pw.zanieczyszczenie.org.ui.map.MapView;

import java.util.concurrent.ThreadLocalRandom;

public class Demo extends Application {
    private Model model = new Data(
            new BasicParser(TestUtilities::loadFromTestResources)
    );

    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();

        var x = model.getStationInfoPages();
        for(var el: x) {
            mapView.addPOI(el.getGeographicLat(),
                    el.getGeographicLon(),
                    el.color("st"),
                    e -> System.out.println(el.getId()) // tutaj handler żeby zmienić prawy pasek
            );
        }

        var pane = mapView.getPane();
        VBox root = new VBox(pane);
        stage.setScene(new Scene(root));
        stage.setTitle("Mappe demo");
        stage.show();
    }

    public static void run(){
        launch();
    }
}
