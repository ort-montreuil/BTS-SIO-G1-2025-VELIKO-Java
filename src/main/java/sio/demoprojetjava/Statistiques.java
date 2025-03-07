package sio.demoprojetjava;

import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import sio.demoprojetjava.controller.StatistiquesController;
import sio.demoprojetjava.tools.DataSourceProvider;

import javax.sql.DataSource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Statistiques implements Initializable {
    @javafx.fxml.FXML
    private BarChart graph1;
    StatistiquesController statistiquesController;
    DataSourceProvider dataSourceProvider;


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
    }
}
