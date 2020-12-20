package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private ImageView image1;
    @FXML
    private TextField stan_powietrza;

    public void initialize() {
        image1.setImage(new Image("obr1.png"));
        stan_powietrza.setMouseTransparent(true);
        stan_powietrza.setStyle("-fx-background-color: rgba(53,89,119,0);");
        stan_powietrza.setText("dobry");

    }

}
