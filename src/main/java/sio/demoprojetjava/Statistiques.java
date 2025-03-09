package sio.demoprojetjava;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sio.demoprojetjava.controller.StatistiquesController;
import sio.demoprojetjava.tools.DataSourceProvider;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Statistiques implements Initializable {
    @javafx.fxml.FXML
    private BarChart graph1;
    StatistiquesController statistiquesController;
    DataSourceProvider dataSourceProvider;
    @javafx.fxml.FXML
    private TableColumn tcNbResa;
    @javafx.fxml.FXML
    private TableView tvResa;
    @javafx.fxml.FXML
    private TableColumn tcDate;
    @javafx.fxml.FXML
    private AnchorPane ap2;
    @javafx.fxml.FXML
    private AnchorPane ap1;
    @javafx.fxml.FXML
    private TextField txtNbUser;
    @javafx.fxml.FXML
    private TextField txtNbStation;
    @javafx.fxml.FXML
    private BarChart graph3;
    @javafx.fxml.FXML
    private AnchorPane ap3;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dataSourceProvider = new DataSourceProvider();
        statistiquesController = new StatistiquesController();


        // Récupérer les données de la base de données via ta méthode existante
        HashMap<String, Integer> data = statistiquesController.getNbReservations();

        // Créer une série pour le BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Réservations par station");

        // Ajouter les données à la série
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Nettoyer et ajouter la série au graphique
        graph1.getData().clear();
        graph1.getData().add(series);




        // Population graph2
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date_resa"));
        tcNbResa.setCellValueFactory(new PropertyValueFactory<>("nbResa"));
        try {
            tvResa.setItems(FXCollections.observableArrayList(statistiquesController.getNbResa()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // txtNbUser et txtNbStation
        txtNbStation.setText(String.valueOf(statistiquesController.getLesStations()));
        txtNbUser.setText(String.valueOf(statistiquesController.getLesUser()));

        //graph3

        HashMap<String, Integer> data3 = statistiquesController.getUserPlusActif();

        XYChart.Series<String, Number> series3 = new XYChart.Series<>();
        series3.setName("Utilisateurs les plus actifs");

        for (Map.Entry<String, Integer> entry : data3.entrySet()) {
            series3.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        graph3.getData().clear();
        graph3.getData().add(series3);

    }
}
