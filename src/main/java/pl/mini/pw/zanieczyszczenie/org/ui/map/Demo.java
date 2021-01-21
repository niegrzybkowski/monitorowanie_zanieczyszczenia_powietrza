package pl.mini.pw.zanieczyszczenie.org.ui.map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Demo extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MapView mapView = new MapView();
        var view = mapView.getView();
        Pane container = new Pane(view);

        view.fitWidthProperty().bind(container.widthProperty());
        view.fitHeightProperty().bind(container.heightProperty());

        VBox root = new VBox(container);
        stage.setScene(new Scene(root));
        stage.setTitle("Pluto explorer");
        stage.show();
    }

    public static void run(){
        launch();
    }
}
