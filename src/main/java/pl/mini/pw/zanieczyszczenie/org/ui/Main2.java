package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main2 extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("main_panel.fxml")));
        primaryStage.setTitle("Monitorowanie zanieczyszczenia powietrza");
        primaryStage.setScene(new Scene(root, 683, 482));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
