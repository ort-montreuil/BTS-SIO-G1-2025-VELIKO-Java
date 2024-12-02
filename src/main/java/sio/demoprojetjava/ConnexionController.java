package sio.demoprojetjava;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexionController {

    @FXML
    private Button btnValiderConnexion;
    @FXML
    private TextField txtIdConnexion;
    @FXML
    private PasswordField txtMdpConnexion;

    @FXML
    public void initialize() {
        System.out.println("Initialisation des composants...");
    }

    private boolean authenticate(String username, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = DataSourceProvider.getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la connexion à la base de données.", Alert.AlertType.ERROR);
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void btnCLickedValiderConnexion(Event event) {
        String username = txtIdConnexion.getText().trim();
        String password = txtMdpConnexion.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
            return;
        }

        if (authenticate(username, password)) {
            showAlert("Succès", "Connexion réussie !", Alert.AlertType.INFORMATION);
            loadNewScene("/sio/demoprojetjava/another-view.fxml");
        } else {
            showAlert("Erreur", "Identifiant ou mot de passe incorrect.", Alert.AlertType.ERROR);
        }
    }

    private void loadNewScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) btnValiderConnexion.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            showAlert("Erreur", "Une erreur est survenue lors du chargement de la nouvelle vue.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
