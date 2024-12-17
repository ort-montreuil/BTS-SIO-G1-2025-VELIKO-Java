package sio.demoprojetjava;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sio.demoprojetjava.controller.UserController;
import sio.demoprojetjava.model.User;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    UserController userController;

    @javafx.fxml.FXML
    private TableColumn tcPrenom;
    @javafx.fxml.FXML
    private Button btnBloquer;
    @javafx.fxml.FXML
    private TableView<User> tvGestionUsers;
    @javafx.fxml.FXML
    private Button btnChangerMdp;
    @javafx.fxml.FXML
    private TableColumn tcId;
    @javafx.fxml.FXML
    private TableColumn tcNom;
    @javafx.fxml.FXML
    private TableColumn tcVerifier;
    @javafx.fxml.FXML
    private TableColumn tcMail;
    @javafx.fxml.FXML
    private Button btnSupprimer;
    @javafx.fxml.FXML
    private TableColumn tcBloquer;
    @javafx.fxml.FXML
    private TableColumn tcForcerMdp;

    DataSourceProvider macnx;

    @javafx.fxml.FXML
    public void btnChangerMdpClicked(MouseEvent mouseEvent) {
    }

    public void btnSupprimerClicked(MouseEvent mouseEvent) {
        User selectedUser = tvGestionUsers.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce compte ?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int idUser = selectedUser.getIdUser();
                    userController.deleteUser(idUser);
                    tvGestionUsers.getItems().remove(selectedUser);

                    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmationAlert.setTitle("Suppression réussie");
                    confirmationAlert.setHeaderText("L'utilisateur a été supprimé.");
                    confirmationAlert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText("Une erreur s'est produite lors de la suppression.");
                    errorAlert.showAndWait();
                }
            }
        });
    }

    @javafx.fxml.FXML
    public void btnBloquerClicked(MouseEvent mouseEvent) throws SQLException {
        User selectionnee = tvGestionUsers.getSelectionModel().getSelectedItem();
        if (selectionnee == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Veuillez sélectionner un utilisateur.");
            alert.setContentText("Aucun utilisateur n'a été sélectionné pour cette action.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de l'action");
        String action = userController.isBlocker(selectionnee) ? "bloquer" : "débloquer";
        alert.setHeaderText("Êtes-vous sûr de vouloir " + action + " ce compte ?");
        alert.setContentText("Cette action peut être modifiée ultérieurement.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            int idUser = selectionnee.getIdUser();

            if (userController.isBlocker(selectionnee)) {
                userController.deblocker(idUser);
            } else {
                userController.blocker(idUser);
            }

            tvGestionUsers.setItems(FXCollections.observableArrayList(userController.getAll()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        macnx = new DataSourceProvider();
        userController = new UserController();
        tcId.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        tcNom.setCellValueFactory(new PropertyValueFactory<>("NomUser"));
        tcPrenom.setCellValueFactory(new PropertyValueFactory<>("PrenomUser"));
        tcMail.setCellValueFactory(new PropertyValueFactory<>("emailUser"));
        tcVerifier.setCellValueFactory(new PropertyValueFactory<>("VerifUser"));
        tcBloquer.setCellValueFactory(new PropertyValueFactory<>("BloquerUser"));
        tcForcerMdp.setCellValueFactory(new PropertyValueFactory<>("ChangerMdpUser"));
        tvGestionUsers.setEditable(true);

        try {
            tvGestionUsers.setItems(FXCollections.observableArrayList(userController.getAll()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}