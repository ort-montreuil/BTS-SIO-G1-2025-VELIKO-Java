package sio.demoprojetjava;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import sio.demoprojetjava.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private final String apiUrl = "http://localhost"; // Replace with the correct URL
    private final int apiPort = 9042; // Ensure the port matches your API
    @FXML
    private Pane mapContainer;
    @FXML
    private Label welcomeText;
    private ConnexionController connexionController;
    private User user;
    @FXML
    private Button btnAdmin;


    @FXML
    public void onShowMapClick() {
        // Optional: Add behavior for a button click to reload the map
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Create a WebView instance
        connexionController = new ConnexionController();
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Load the HTML file
        String htmlPath = Objects.requireNonNull(getClass().getResource("/sio/demoprojetjava/carte.html")).toExternalForm(); // Ensure this file exists in 'resources'
        webEngine.load(htmlPath);
        // Clear the container and add the WebView
        mapContainer.getChildren().clear();
        mapContainer.getChildren().add(webView);
        webView.prefWidthProperty().bind(mapContainer.widthProperty());
        webView.prefHeightProperty().bind(mapContainer.heightProperty());

        // Bind the size of the WebView to the map container
        webView.prefWidthProperty().bind(mapContainer.widthProperty());
        webView.prefHeightProperty().bind(mapContainer.heightProperty());

        // Fetch data and process stations
        List<JsonObject> stations = fetchStationData();
        System.out.println("Fetched Stations: " + stations);

        // Ajoutez un écouteur pour exécuter le script une fois que la page est chargée
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == javafx.concurrent.Worker.State.SUCCEEDED) {
                // Transmettez les données des stations au fichier HTML
                webEngine.executeScript("updateMap(" + new Gson().toJson(stations) + ")");
            }
        });



    }

    private List<JsonObject> fetchStationData() {
        List<JsonObject> stations = new ArrayList<>();
        try {
            // Fetch station information
            JsonArray stationInfos = fetchJsonArray("/api/stations");
            JsonArray stationStatuses = fetchJsonArray("/api/stations/status");

            if (stationInfos != null && stationStatuses != null) {
                for (var info : stationInfos) {
                    JsonObject infoObj = info.getAsJsonObject();
                    for (var status : stationStatuses) {
                        JsonObject statusObj = status.getAsJsonObject();
                        if (infoObj.get("station_id").getAsString().equals(statusObj.get("station_id").getAsString())) {
                            JsonObject stationData = new JsonObject();
                            stationData.addProperty("nom", infoObj.get("name").getAsString());
                            stationData.addProperty("lat", infoObj.get("lat").getAsDouble());
                            stationData.addProperty("lon", infoObj.get("lon").getAsDouble());
                            stationData.addProperty("velodispo", statusObj.get("num_bikes_available").getAsInt());
                            stationData.addProperty("velomecha", statusObj.get("num_bikes_available_types")
                                    .getAsJsonArray().get(0).getAsJsonObject().get("mechanical").getAsInt());
                            stationData.addProperty("veloelec", statusObj.get("num_bikes_available_types")
                                    .getAsJsonArray().get(1).getAsJsonObject().get("ebike").getAsInt());
                            stationData.addProperty("id", statusObj.get("station_id").getAsString());
                            stations.add(stationData);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stations;
    }


    private JsonArray fetchJsonArray(String endpoint) throws Exception {
        // Setup HTTP connection
        URL url = new URL(apiUrl + ":" + apiPort + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Parse response
        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            // Parse JSON using Gson
            Gson gson = new Gson();
            return gson.fromJson(response.toString(), JsonArray.class);
        } else {
            throw new RuntimeException("Failed to fetch data: HTTP " + responseCode);
        }
    }


    @FXML
    public void btnAdminCliked(Event event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Admin");
            stage.setScene(scene);
            stage.show();
            ((Stage) btnAdmin.getScene().getWindow()).close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}