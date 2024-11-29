package sio.demoprojetjava;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class HelloController {
    @FXML
    private WebView wvCarte;

    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        DataSourceProvider provider;
        try {
            provider = new DataSourceProvider();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Charger la carte HTML
        String mapHtml;
        try {
            mapHtml = new String(Files.readAllBytes(Paths.get("src/main/resources/sio/demoprojetjava/carte.html")));
        } catch (IOException e) {
            throw new RuntimeException("Erreur de chargement de la carte HTML", e);
        }

        WebEngine webEngine = wvCarte.getEngine();
        webEngine.loadContent(mapHtml);

        /*// Charger les stations
        Task<List<Station>> task = new Task<>() {
            @Override
            protected List<Station> call() throws Exception {
                return Station.getStations();
            }

            @Override
            protected void succeeded() {
                List<Station> stations = getValue();

                // Construction du script JavaScript
                StringBuilder script = new StringBuilder();
                script.append("function loadStations() {");
                for (Station station : stations) {
                    script.append("var marker = L.marker([")
                            .append(station.getLatitude()).append(", ").append(station.getLongitude()).append("])")
                            .append(".bindPopup('Station: [")
                            .append(station.getLatitude()).append(", ").append(station.getLongitude()).append("]');")
                            .append("markerClusters.addLayer(marker);");
                }
                script.append("macarte.addLayer(markerClusters); }"); // macarte doit être vérifié côté HTML

                // Injection et exécution du script dans WebView
                webEngine.executeScript(script.toString());
                webEngine.executeScript("loadStations();");
            }


            @Override
            protected void failed() {
                Throwable error = getException();
                error.printStackTrace();
            }
        };
        new Thread(task).start();*/
    }
}