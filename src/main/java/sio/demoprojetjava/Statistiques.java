package sio.demoprojetjava;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
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
    @javafx.fxml.FXML
    private AnchorPane ap4;
    @javafx.fxml.FXML
    private AnchorPane ap5;
    @javafx.fxml.FXML
    private BarChart graph5;
    @javafx.fxml.FXML
    private BarChart graph4;
    @javafx.fxml.FXML
    private PieChart graph6;
    @javafx.fxml.FXML
    private AnchorPane ap6;
    @javafx.fxml.FXML
    private AnchorPane ap7;
    @javafx.fxml.FXML
    private TableColumn tcNom;
    @javafx.fxml.FXML
    private TableColumn tcPopularite;
    @javafx.fxml.FXML
    private AnchorPane ap8;
    @javafx.fxml.FXML
    private TableView tvFavoris;
    @javafx.fxml.FXML
    private PieChart graph8;


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


        // Population tab
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




        //graph4
        // Top 10 Utilisateurs avec le plus de réservations
        XYChart.Series<String, Number> series4 = new XYChart.Series<>();
        series4.setName("Top 10 Utilisateurs");

        // Récupérer les données des top 10 utilisateurs
        HashMap<String, Integer> topUsers = statistiquesController.getTop10UsersReservations();
        for (Map.Entry<String, Integer> entry : topUsers.entrySet()) {
            series4.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        // Ajouter la série au graph4
        graph4.getData().clear();
        graph4.getData().add(series4);


    //graph5
        // graph5, Top 10 Stations avec le plus de capacité
        XYChart.Series<String, Number> series5 = new XYChart.Series<>();
        series5.setName("Top 10 Stations avec le plus de capacité");

        // Récupérer les données des stations avec leur capacité
        HashMap<String, Integer> topStations = statistiquesController.getTop10StationsCapacite();
        if (topStations.isEmpty()) {
            System.out.println("Aucune donnée pour les stations !");
        } else {
            for (Map.Entry<String, Integer> entry : topStations.entrySet()) {
                XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(entry.getKey(), entry.getValue());
                series5.getData().add(dataPoint);


                Tooltip tooltip = new Tooltip("Station: " + entry.getKey() + "\nCapacité: " + entry.getValue());
                Tooltip.install(dataPoint.getNode(), tooltip);
            }


            graph5.lookup(".chart-legend").setStyle("-fx-font-size: 10px;");
            graph5.getXAxis().lookup(".axis-label").setStyle("-fx-font-size: 10px;");
            graph5.getYAxis().lookup(".axis-label").setStyle("-fx-font-size: 10px;");


        }

        // Ajouter la série au graphique graph5
        graph5.getData().clear();
        graph5.getData().add(series5);




        //graph6
        HashMap<String, Integer> dataAge = null; // Récupère les données de répartition d'âge
        try {
            dataAge = statistiquesController.getAgesUser();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        graph6.getData().clear();

        // Ajouts les données au PieChart
        for (Map.Entry<String, Integer> entry : dataAge.entrySet()) {
            graph6.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }


        //graph7






        //graph 8

        //Type de velo reservé
        HashMap<String, Integer> typeVeloData;
        typeVeloData = statistiquesController.getTypeVeloByReservation(); // Appel à la méthode du contrôleur

        // Ajouts les données au PieChart
        for (Map.Entry<String, Integer> entry : typeVeloData.entrySet()) {
            // Renommer la variable 'data' pour éviter les conflits
            PieChart.Data pieChartData = new PieChart.Data(entry.getKey(), entry.getValue());
            graph8.getData().add(pieChartData);

            // Ajouts un Tooltip pour chaque segment du graphique
            Tooltip t = new Tooltip(entry.getKey() + " : " + entry.getValue() + " réservations");
            t.setStyle("-fx-font-weight: bold");
            Tooltip.install(pieChartData.getNode(), t);
        }




    }
}





