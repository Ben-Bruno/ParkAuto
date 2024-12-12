package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import connexion.Mysql;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import verification.PasswordHacher;

public class ConnexionController {

    @FXML
    private TextField name; // Pour l'email

    @FXML
    private PasswordField pass;

    @FXML
    private Button connect;

    @FXML
    void seConnecter(ActionEvent event) {
        String email = name.getText().trim();
        String password = pass.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur de connexion", "Veuillez remplir tous les champs.");
            return;
        }

        // Hachez le mot de passe avant de l'envoyer à la base de données
        String hashedPassword = PasswordHacher.hashPassword(password);

        Mysql mysql = new Mysql();
        try (Connection conn = mysql.connect()) {
            if (conn == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Impossible de se connecter à la base de données.");
                return;
            }

            // Préparez et exécutez la requête
            String query = "SELECT nomGestionnaire, role FROM gestionnaire WHERE emailGestionnaire = ? AND pass = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Récupérez le rôle et le nom du gestionnaire
                String nomGestionnaire = rs.getString("nomGestionnaire");
                String role = rs.getString("role");

                if ("admin".equalsIgnoreCase(role)) {
                    showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Bienvenue, Administrateur " + nomGestionnaire + " !");
                    loadPage("/view/AdminMenuView.fxml");
                } else if ("gestionnaire".equalsIgnoreCase(role)) {
                    showAlert(Alert.AlertType.INFORMATION, "Connexion réussie", "Bienvenue, " + nomGestionnaire + " !");
                    loadPage("/view/MenuGestView.fxml");
                } else {
                    showAlert(Alert.AlertType.WARNING, "Erreur", "Rôle non reconnu.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Échec de connexion", "Email ou mot de passe incorrect.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la connexion.");
        }
    }


    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadPage(String fxmlFile) {
        try {
            AnchorPane pane = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) connect.getScene().getWindow();
            stage.getScene().setRoot(pane);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Erreur de navigation", "Impossible de charger la page.");
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation si nécessaire
    }
}
