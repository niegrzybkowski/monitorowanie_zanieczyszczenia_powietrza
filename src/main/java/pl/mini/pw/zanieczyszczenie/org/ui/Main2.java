package pl.mini.pw.zanieczyszczenie.org.ui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Objects;

public class Main2 extends Application {

    private MapView mapView;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_panel.fxml")));
        primaryStage.setTitle("Monitorowanie zanieczyszczenia powietrza");
        primaryStage.setScene(new Scene(root, 804, 573));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    private void showMap(Double lat, Double lon) {
        mapView = new MapView();
        PoiLayer poiLayer = new PoiLayer();
        MapPoint mapPoint = new MapPoint(lat, lon);
        poiLayer.addPoint(mapPoint, new Circle(7, Color.RED));
        mapView.setZoom(16);
        mapView.addLayer(poiLayer);
        mapView.flyTo(0.1, mapPoint, 0.1);
        tabMap.setContent(mapView);
   }


    public static void main(String[] args) {
        launch(args);
    }
}
