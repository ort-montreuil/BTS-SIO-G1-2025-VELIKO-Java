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
import sio.demoprojetjava.controller.UserController;
import sio.demoprojetjava.model.User;
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
    private User user;
    UserController userController;

    DataSourceProvider dataSourceProvider;


    @FXML
    public void initialize() throws SQLException, ClassNotFoundException {
        System.out.println("Initialisation des composants...");
        userController = new UserController();
        dataSourceProvider = new DataSourceProvider();
    }




    @FXML
    public void btnCLickedValiderConnexion(Event event) throws SQLException, IOException {
        try {
            if (userController.checkCredentials(txtIdConnexion.getText(), txtMdpConnexion.getText())) {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Hello");
                stage.setScene(scene);
                stage.show();
                ((Stage) btnValiderConnexion.getScene().getWindow()).close();
                System.out.println("c'est bon");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Erreur de connexion");
                alert.setContentText("Pseudo ou mot de passe incorrect");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
