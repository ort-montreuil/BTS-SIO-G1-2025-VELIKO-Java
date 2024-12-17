package sio.demoprojetjava;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sio.demoprojetjava.controller.UserController;
import sio.demoprojetjava.model.User;
import sio.demoprojetjava.tools.DataSourceProvider;

import java.io.IOException;
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
    private ImageView imgRevenir;


    @javafx.fxml.FXML
    public void btnChangerMdpClicked(MouseEvent mouseEvent) {

        User selectedUser = tvGestionUsers.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Veuillez sélectionner un utilisateur.");
            alert.setContentText("Aucun utilisateur n'a été sélectionné pour cette action.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de l'action");
        alert.setHeaderText("Êtes-vous sûr de vouloir forcer le changement de mot de passe pour cet utilisateur ?");
        alert.setContentText("Cette action peut être modifiée ultérieurement.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            int idUser = selectedUser.getIdUser();

            try {
                userController.passwordForced(idUser);
                tvGestionUsers.setItems(FXCollections.observableArrayList(userController.getAll()));
            } catch (SQLException e) {
                e.printStackTrace();
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText("Une erreur s'est produite lors du changement de mot de passe.");
                errorAlert.showAndWait();
            }
        }
    }

    @javafx.fxml.FXML
    public void btnSupprimerClicked(MouseEvent mouseEvent) {
        // Récupérer l'utilisateur sélectionné dans la TableView
        User selectedUser = tvGestionUsers.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // Si aucun utilisateur n'est sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Veuillez sélectionner un utilisateur à supprimer.");
            alert.showAndWait();
            return;
        }

        // Créer une alerte de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de suppression");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce compte ?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        // Afficher l'alerte et attendre la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Si l'utilisateur confirme la suppression
                try {
                    int idUser = selectedUser.getIdUser();

                    // Appeler la méthode pour anonymiser l'utilisateur
                    userController.deleteUser(idUser);

                    // Supprimer l'utilisateur de la TableView après la suppression
                    tvGestionUsers.getItems().remove(selectedUser);

                    // Afficher un message de confirmation
                    Alert confirmationAlert = new Alert(Alert.AlertType.INFORMATION);
                    confirmationAlert.setTitle("Suppression réussie");
                    confirmationAlert.setHeaderText("L'utilisateur a été supprimé.");
                    confirmationAlert.showAndWait();

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Afficher une alerte d'erreur si quelque chose ne va pas
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
        String action = userController.isBlocker(selectionnee) ? "débloquer" : "bloquer";
        alert.setHeaderText("Êtes-vous sûr de vouloir " + action + " ce compte ?");
        alert.setContentText("Cette action peut être modifiée ultérieurement.");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            int idUser = selectionnee.getIdUser();

            if (userController.isBlocker(selectionnee)) {
                userController.deblocker(idUser); // Débloque si l'utilisateur est déjà bloqué
            } else {
                userController.blocker(idUser); // Bloque si l'utilisateur n'est pas encore bloqué
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

    @javafx.fxml.FXML
    public void tvClicked(Event event)throws SQLException {
        User selectedUser = tvGestionUsers.getSelectionModel().getSelectedItem();
        if (userController.isBlocker(selectedUser)) {
            btnBloquer.setText("Débloquer");
        } else {
            btnBloquer.setText("Bloquer");
        }
    }

    public void imgArriereClicked(Event event) {
        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent root = loader.load();

            // Obtenir la scène actuelle et remplacer son contenu
            Stage stage = (Stage) imgRevenir.getScene().getWindow();
            stage.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Afficher une alerte en cas d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger la page hello-view.fxml");
            alert.setContentText("Vérifiez que le fichier existe et qu'il est correctement configuré.");
            alert.showAndWait();
        }
    }
}
