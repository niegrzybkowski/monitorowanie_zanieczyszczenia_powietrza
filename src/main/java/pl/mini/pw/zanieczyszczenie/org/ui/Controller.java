package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Controller {
    @FXML
    private ImageView image1;
    @FXML
    private TextField stan_powietrza;
    @FXML
    private TextField pm25;
    @FXML
    private TextField pm10;
    @FXML
    private TextField no2;
    @FXML
    private TextField co;
    @FXML
    private TextField c6h6;
    @FXML
    private TextField so2;
    @FXML
    private TextField o3;
    @FXML
    private TextField jednostka1;
    @FXML
    private TextField jednostka2;
    @FXML
    private TextField jednostka3;
    @FXML
    private TextField jednostka4;
    @FXML
    private TextField jednostka5;
    @FXML
    private TextField jednostka6;
    @FXML
    private TextField jednostka7;
    @FXML
    private Rectangle prostokat0;
    @FXML
    private Rectangle prostokat1;
    @FXML
    private Rectangle prostokat2;
    @FXML
    private Rectangle prostokat3;
    @FXML
    private Rectangle prostokat4;
    @FXML
    private Rectangle prostokat5;
    @FXML
    private Rectangle prostokat6;
    @FXML
    private Rectangle prostokat7;
    @FXML
    private LineChart plot1;



    public void initialize() {
        image1.setImage(new Image("obr1.png"));
        stan_powietrza.setMouseTransparent(true);
        stan_powietrza.setStyle("-fx-background-color: rgba(53,89,119,0);");
        stan_powietrza.setText("dobry");
        var list_zan = new ArrayList<TextField>();
        list_zan.add(pm25);
        list_zan.add(pm10);
        list_zan.add(no2);
        list_zan.add(co);
        list_zan.add(c6h6);
        list_zan.add(so2);
        list_zan.add(o3);
        for(TextField t : list_zan){
            t.setMouseTransparent(true);
            t.setStyle("-fx-background-color: rgba(53,89,119,0);");
            t.setText("55");
        }
        var list_jednostek = new ArrayList<TextField>();
        list_jednostek.add(jednostka1);
        list_jednostek.add(jednostka2);
        list_jednostek.add(jednostka3);
        list_jednostek.add(jednostka4);
        list_jednostek.add(jednostka5);
        list_jednostek.add(jednostka6);
        list_jednostek.add(jednostka7);
        for(TextField t : list_jednostek){
            t.setMouseTransparent(true);
            t.setStyle("-fx-background-color: rgba(53,89,119,0);");
            t.setText("\u00B5"+"g/m"+"\u00B3");
        }
        var list_prostokaty = new ArrayList<Rectangle>();
        list_prostokaty.add(prostokat0);
        list_prostokaty.add(prostokat1);
        list_prostokaty.add(prostokat2);
        list_prostokaty.add(prostokat3);
        list_prostokaty.add(prostokat4);
        list_prostokaty.add(prostokat5);
        list_prostokaty.add(prostokat6);
        list_prostokaty.add(prostokat7);

        for(Rectangle t : list_prostokaty){
            t.setFill(Color.web("#1aff1a"));
        }

        plot1.setTitle("Wykres 1");
    }

}
