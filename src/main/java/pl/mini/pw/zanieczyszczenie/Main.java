package pl.mini.pw.zanieczyszczenie;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.mini.pw.zanieczyszczenie.org.ui.Controller;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_panel.fxml")));
        primaryStage.setTitle("Monitorowanie zanieczyszczenia powietrza");
        primaryStage.setScene(new Scene(root, 804,  544));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Controller.killLoadingThread();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
