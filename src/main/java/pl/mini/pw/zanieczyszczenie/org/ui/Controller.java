package pl.mini.pw.zanieczyszczenie.org.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;

import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import pl.mini.pw.zanieczyszczenie.communicator.BasicParser;
import pl.mini.pw.zanieczyszczenie.model.Data;
import pl.mini.pw.zanieczyszczenie.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleConsumer;

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
    private Rectangle prostokatstanklik;
    @FXML
    private Rectangle prostokatpm25klik;
    @FXML
    private Rectangle prostokatpm10klik;
    @FXML
    private Rectangle prostokatno2klik;
    @FXML
    private Rectangle prostokatcoklik;
    @FXML
    private Rectangle prostokatc6h6klik;
    @FXML
    private Rectangle prostokatso2klik;
    @FXML
    private Rectangle prostokato3klik;
    @FXML
    private AnchorPane map;
    @FXML
    private TextField ladowanie;
    @FXML
    private Button refreshbutton;
    @FXML
    private Button okbutton;
    @FXML
    ToggleGroup selected;
    @FXML
    VBox plotpollution;

    private final Model model = new Data(
            new BasicParser(BasicParser::loadFromTestResources)
    );

    private int currentStation = -1;

    private MapView mapView;


    public void initialize() {
        mapView = new MapView();


        var pane = mapView.getPane();
        VBox root = new VBox(pane);

        map.getChildren().setAll(root);

        Model model = new Data(
                new BasicParser(BasicParser::loadFromTestResources)
        );


        PlotView pv = new PlotView();
        pv.setCurrent(model.getReadingsPage(14, "PM25"));

        VBox vbox = new VBox(pv.getChart());

        plotpollution.getChildren().setAll(vbox);

        ladowanie.setText("");
        ladowanie.setMouseTransparent(true);
        ladowanie.setStyle("-fx-background-color: rgba(53,89,119,0);");
        stan_powietrza.setMouseTransparent(true);
        stan_powietrza.setStyle("-fx-background-color: rgba(53,89,119,0);");
        stan_powietrza.setText("");
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
            t.setText("");
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

        var list_prostokaty_klik = new ArrayList<Rectangle>();
        list_prostokaty.add(prostokatstanklik);
        list_prostokaty.add(prostokatpm25klik);
        list_prostokaty.add(prostokatpm10klik);
        list_prostokaty.add(prostokatno2klik);
        list_prostokaty.add(prostokatcoklik);
        list_prostokaty.add(prostokatc6h6klik);
        list_prostokaty.add(prostokatso2klik);
        list_prostokaty.add(prostokato3klik);



        prostokatpm25klik.setOnMouseClicked(t -> makeChart("PM25"));
        prostokatpm10klik.setOnMouseClicked(t -> makeChart("PM10"));
        prostokatno2klik.setOnMouseClicked(t -> makeChart("NO2"));
        prostokatcoklik.setOnMouseClicked(t -> makeChart("CO"));
        prostokatc6h6klik.setOnMouseClicked(t -> makeChart("C6H6"));
        prostokatso2klik.setOnMouseClicked(t -> makeChart("SO2"));
        prostokato3klik.setOnMouseClicked(t -> makeChart("O3"));
        prostokatstanklik.setOnMouseClicked(t -> System.out.println("co?"));

        setprostokatColor(pm25, prostokatpm25, 13, 37, 61, 85, 121);
        setprostokatColor(pm10, prostokatpm10, 21, 61, 101, 141, 201);
        setprostokatColor(no2, prostokatno2, 41, 101, 151, 201, 401);
        setprostokatColor(co, prostokatco, 3, 7, 11, 15, 21);
        setprostokatColor(c6h6, prostokatc6h6, 6, 11, 16, 21, 51);
        setprostokatColor(so2, prostokatso2, 51, 101, 201, 351, 501);
        setprostokatColor(o3, prostokato3, 71, 121, 151, 181, 241);
        setprostokatStanColor(stan_powietrza, prostokatstan);



        EventHandler<ActionEvent> refreshbuttonHandler = event -> {
            ladowanie.setText("Ładuję");
            addStations();
            ladowanie.setText("");
            event.consume();
        };
        refreshbutton.setOnAction(refreshbuttonHandler);

        EventHandler<ActionEvent> okbuttonHandler = event -> {
            System.out.println("ok");
            event.consume();
        };
        okbutton.setOnAction(okbuttonHandler);

        updateButtons(562);
    }

    public void makeChart(String key) {
        System.out.println("stacja: " + currentStation + " klucz:"+ key);

        PlotView pv = new PlotView();
        pv.setCurrent(model.getReadingsPage(currentStation, key));

        VBox vbox = new VBox(pv.getChart());
        plotpollution.getChildren().setAll(vbox);

    }

    public void addStations(){
        try {
            for (var el : model.getStationInfoPages()) {
                mapView.addPOI(el.getGeographicLat(),
                        el.getGeographicLon(),
                        el.color(((RadioButton) selected.getSelectedToggle()).getId().toLowerCase(Locale.ROOT)),
                        e -> updateButtons(el.getId()) // tutaj handler żeby zmienić prawy pasek
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading data! Ruin has come to our family...");
        }
    }

    public void updateButtons(int idStacji) {
        currentStation = idStacji;
        for(String key: List.of("PM25", "PM10", "NO2", "CO", "C6H6", "SO2", "O3")) {
            var page = model.getReadingsPage(idStacji, key);
            if(page != null && page.getObservations().size() !=0) {
                double stezenie = page.getObservations().get(0).getValue();
                keyToFun(Math.round(stezenie*10)/10.0, key);
            } else {
                keyToFun(-1, key);
            }
        }
        updatestan(model.getStationInfoPage(idStacji).getStringIndex());
    }

    public void keyToFun(double stezenie, String key){
        switch (key) {
            case "PM25": updatepm25(stezenie);
            case "PM10": updatepm10(stezenie);
            case "NO2": updateno2(stezenie);
            case "CO": updateco(stezenie);
            case "C6H6": updatec6h6(stezenie);
            case "SO2": updateso2(stezenie);
            case "O3": updateo3(stezenie);

        }
    }

    public void updatepm25(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        pm25.setText(stezenieSt);
        setprostokatColor(pm25, prostokatpm25, 13, 37, 61, 85, 121);
    }

    public void updatepm10(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        pm10.setText(stezenieSt);
        setprostokatColor(pm10, prostokatpm10, 21, 61, 101, 141, 201);
    }

    public void updateno2(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        no2.setText(stezenieSt);
        setprostokatColor(no2, prostokatno2, 41, 101, 151, 201, 401);
    }

    public void updateco(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        co.setText(stezenieSt);
        setprostokatColor(co, prostokatco, 3000, 7000, 11000, 15000, 21000);
    }

    public void updatec6h6(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        c6h6.setText(stezenieSt);
        setprostokatColor(c6h6, prostokatc6h6, 6, 11, 16, 21, 51);
    }

    public void updateso2(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }        so2.setText(stezenieSt);
        setprostokatColor(so2, prostokatso2, 51, 101, 201, 351, 501);
    }

    public void updateo3(double stezenie){
        String stezenieSt = "-";
        if(stezenie > 0) {
            stezenieSt = String.valueOf(stezenie);
        }
        o3.setText(stezenieSt);
        setprostokatColor(o3, prostokato3, 71, 121, 151, 181, 241);
    }

    public void updatestan(String stan){
        stan_powietrza.setText(stan);
        setprostokatStanColor(stan_powietrza, prostokatstan);
    }

    public void setprostokatColor(TextField wartosc, Rectangle prostokat, int bdb, int db, int umiark, int dost, int zly){
        if(wartosc.getCharacters().isEmpty() || wartosc.getCharacters().toString().equals("-")){
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
        else if((Double.parseDouble(wartosc.getCharacters().toString())) <= zly &&
                (Double.parseDouble(wartosc.getCharacters().toString())) > 0){
            prostokat.setFill(Color.web("#ff3300"));
        }
        else{
            prostokat.setFill(Color.web("#e60000")); // color gdy null
        }
    }

    public void setprostokatStanColor(TextField wartosc, Rectangle prostokat) {
        if ((wartosc.getCharacters().toString()).equals("Brak danych")|| wartosc.getCharacters().isEmpty()) {
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
