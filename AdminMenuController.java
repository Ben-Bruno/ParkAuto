package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AdminMenuController {

    @FXML
    private Button guide;

    @FXML
    private Button retour;

    @FXML
    private Button deconnect;

    @FXML
    private Button client;

    @FXML
    private Button vehicule;

    @FXML
    private Button gestionnaire;

    @FXML
    private Button reservation;

    @FXML
    private Button stats;

    @FXML
    void client(ActionEvent event) {
        loadPage("/view/GestionClientView.fxml");
    }

    @FXML
    void gestionnaire(ActionEvent event) {
        loadPage("/view/GestionGestionnaireView.fxml");
    }

    @FXML
    void guide(ActionEvent event) {
        loadPage("/view/GuideView.fxml");
    }

    @FXML
    void reservation(ActionEvent event) {
        loadPage("/view/ReservationView.fxml");
    }

    @FXML
    void retour(ActionEvent event) {
        loadPage("/view/ConnexionView.fxml");
    }

    @FXML
    void seDeconnecter(ActionEvent event) {
        // Redirection vers la page de connexion
        loadPage("/view/ConnexionView.fxml");
    }

    @FXML
    void vehicule(ActionEvent event) {
        loadPage("/view/GestionVehiculeView.fxml");
    }

    @FXML
    void stats(ActionEvent event) {
    	   loadPage("/view/Statistiques.fxml");

    }
    private void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane pane = loader.load();

            Stage stage = (Stage) guide.getScene().getWindow();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de chargement", "Impossible de charger la page : " + fxmlFile);
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
