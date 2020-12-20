package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private ImageView image1;

    public void initialize() {
        image1.setImage(new Image("obr1.png"));
    }

}
