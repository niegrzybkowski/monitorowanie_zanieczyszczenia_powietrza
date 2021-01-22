package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;

import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pl.mini.pw.zanieczyszczenie.org.ui.map.MapView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
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
    @FXML
    private AnchorPane map;
    @FXML
    private ProgressBar pasekpostepu;
    @FXML
    private Button refreshbutton;
    @FXML
    private Button okbutton;



    public void initialize() {
        MapView mapView = new MapView();
        for(int i = 0; i < 500; i++) {
            mapView.addPOI(52.23 + ThreadLocalRandom.current().nextDouble(), 21.01+ ThreadLocalRandom.current().nextDouble(), Color.BLUE,
                    e -> System.out.println("tutaj byłoby coś, żeby otworzyć odpowiednie menu z prawej"));
        }

        var pane = mapView.getPane();
        VBox root = new VBox(pane);

        map.getChildren().setAll(root);

        stan_powietrza.setMouseTransparent(true);
        stan_powietrza.setStyle("-fx-background-color: rgba(53,89,119,0);");
        stan_powietrza.setText("Dobry");
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



        plot1.setTitle("Wykres 1");
        setprostokatColor(pm25, prostokatpm25, 13, 37, 61, 85, 121);
        setprostokatColor(pm10, prostokatpm10, 21, 61, 101, 141, 201);
        setprostokatColor(no2, prostokatno2, 41, 101, 151, 201, 401);
        setprostokatColor(co, prostokatco, 3, 7, 11, 15, 21);
        setprostokatColor(c6h6, prostokatc6h6, 6, 11, 16, 21, 51);
        setprostokatColor(so2, prostokatso2, 51, 101, 201, 351, 501);
        setprostokatColor(o3, prostokato3, 71, 121, 151, 181, 241);
        setprostokatStanColor(stan_powietrza, prostokatstan);

        pasekpostepu.setProgress(-1d); //włączanie paska postępu
        pasekpostepu.setProgress(0d); //wyłączanie paska postępu

        EventHandler<ActionEvent> refreshbuttonHandler = event -> {
            System.out.println("tak");
            event.consume();
        };
        refreshbutton.setOnAction(refreshbuttonHandler);

        EventHandler<ActionEvent> okbuttonHandler = event -> {
            System.out.println("ok");
            event.consume();
        };
        okbutton.setOnAction(okbuttonHandler);



    }

    public void setprostokatColor(TextField wartosc, Rectangle prostokat, int bdb, int db, int umiark, int dost, int zly){
        if(wartosc.getCharacters().isEmpty()){
            prostokat.setFill(Color.web("#737373"));
        }
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= bdb){
            prostokat.setFill(Color.web("#00cc00"));
        }
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= db){
            prostokat.setFill(Color.web("#00ff00"));
        }
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= umiark){
            prostokat.setFill(Color.web("#ffff00"));
        }
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= dost){
            prostokat.setFill(Color.web("#ff6600"));
        }
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= zly){
            prostokat.setFill(Color.web("#ff3300"));
        }
        else{
            prostokat.setFill(Color.web("#e60000"));
        }
    }

    public void setprostokatStanColor(TextField wartosc, Rectangle prostokat) {
        if (wartosc.getCharacters().isEmpty()) {
            prostokat.setFill(Color.web("#737373"));
        } else if ((wartosc.getCharacters().toString()).equals("Bardzo dobry")) {
            prostokat.setFill(Color.web("#00cc00"));
        } else if ((wartosc.getCharacters().toString()).equals("Dobry")) {
            prostokat.setFill(Color.web("#00ff00"));
        } else if ((wartosc.getCharacters().toString()).equals("Umiarkowany")) {
            prostokat.setFill(Color.web("#ffff00"));
        } else if ((wartosc.getCharacters().toString()).equals("Dostateczny")) {
            prostokat.setFill(Color.web("#ff6600"));
        } else if ((wartosc.getCharacters().toString()).equals("Zły")) {
            prostokat.setFill(Color.web("#ff3300"));
        } else {
            prostokat.setFill(Color.web("#e60000"));
        }
    }
}
