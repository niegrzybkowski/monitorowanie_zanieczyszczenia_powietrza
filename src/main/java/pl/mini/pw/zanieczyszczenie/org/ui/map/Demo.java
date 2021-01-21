package pl.mini.pw.zanieczyszczenie.org.ui.map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

public class Demo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();
        for(int i = 0; i < 500; i++) {
            mapView.addPOI(52.23 + ThreadLocalRandom.current().nextDouble(), 21.01+ ThreadLocalRandom.current().nextDouble(), Color.BLUE,
                    e -> System.out.println("tutaj byłoby coś, żeby otworzyć odpowiednie menu z prawej"));
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
