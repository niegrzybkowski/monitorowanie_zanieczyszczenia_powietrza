package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.communicator.pages.ReadingsPage;

public class OknoWykres {

    public static void popUp(ReadingsPage readingsPage){
        Stage stage = new Stage();

        stage.setTitle("Wykres 1");

        PlotView pv = new PlotView(true);
        pv.setCurrent(readingsPage);
        pv.updateChart();

        VBox vbox = new VBox(pv.getChart());

        Scene scene = new Scene(vbox, 400, 200);

        stage.setScene(scene);
        stage.setHeight(400);
        stage.setWidth(1200);

        stage.show();
    }
}
