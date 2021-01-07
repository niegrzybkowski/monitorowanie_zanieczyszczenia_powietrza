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
    private TextField jednostkapm25;
    @FXML
    private TextField jednostkapm10;
    @FXML
    private TextField jednostkano2;
    @FXML
    private TextField jednostkaco;
    @FXML
    private TextField jednostkac6h6;
    @FXML
    private TextField jednostkaso2;
    @FXML
    private TextField jednostkao3;
    @FXML
    private Rectangle prostokatstan;
    @FXML
    private Rectangle prostokatpm25;
    @FXML
    private Rectangle prostokatpm10;
    @FXML
    private Rectangle prostokatno2;
    @FXML
    private Rectangle prostokatco;
    @FXML
    private Rectangle prostokatc6h6;
    @FXML
    private Rectangle prostokatso2;
    @FXML
    private Rectangle prostokato3;
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

        list_jednostek.add(jednostkapm25);
        list_jednostek.add(jednostkapm10);
        list_jednostek.add(jednostkano2);
        list_jednostek.add(jednostkaco);
        list_jednostek.add(jednostkac6h6);
        list_jednostek.add(jednostkaso2);
        list_jednostek.add(jednostkao3);

        for(TextField t : list_jednostek){
            t.setMouseTransparent(true);
            t.setStyle("-fx-background-color: rgba(53,89,119,0);");
            t.setText("\u00B5"+"g/m"+"\u00B3");
        }
        var list_prostokaty = new ArrayList<Rectangle>();
        list_prostokaty.add(prostokatstan);
        list_prostokaty.add(prostokatpm25);
        list_prostokaty.add(prostokatpm10);
        list_prostokaty.add(prostokatno2);
        list_prostokaty.add(prostokatco);
        list_prostokaty.add(prostokatc6h6);
        list_prostokaty.add(prostokatso2);
        list_prostokaty.add(prostokato3);

        for(Rectangle t : list_prostokaty){
            t.setFill(Color.web("#1aff1a"));
        }

        plot1.setTitle("Wykres 1");
        setprostokatpm25Color();

    }

    public void setprostokatpm25Color(){
        if(pm25.getCharacters().isEmpty()){
            prostokatpm25.setFill(Color.web("#737373"));
        }
        else if((Double.parseDouble(pm25.getCharacters().toString())) < 13){
            prostokatpm25.setFill(Color.web("#00cc00"));
        }
        else if((Double.parseDouble(pm25.getCharacters().toString())) < 37){
            prostokatpm25.setFill(Color.web("#00ff00"));
        }
        else if((Double.parseDouble(pm25.getCharacters().toString())) < 61){
            prostokatpm25.setFill(Color.web("#ffff00"));
        }
        else if((Double.parseDouble(pm25.getCharacters().toString())) < 85){
            prostokatpm25.setFill(Color.web("#ff6600"));
        }
        else if((Double.parseDouble(pm25.getCharacters().toString())) < 121){
            prostokatpm25.setFill(Color.web("#ff3300"));
        }
        else{
            prostokatpm25.setFill(Color.web("#e60000"));
        }
    }

}
