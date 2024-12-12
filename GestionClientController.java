package controller;

import java.io.IOException;

import dao.GestionClientDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Client;

public class GestionClientController {
	
	private String role; // "gestionnaire" ou "administrateur"

	// Méthode pour définir le rôle après connexion
	public void setRole(String role) {
	    this.role = role;
	}



    @FXML
    private TextField nomCl;

    @FXML
    private TextField prenomCl;

    @FXML
    private TextField addCl;

    @FXML
    private TextField contactCl;

    @FXML
    private Button modifierCl;

    @FXML
    private Button ajouterCl;

    @FXML
    private Button rechercherClbtn;
    
    @FXML
    private Button deconnect;
    
    @FXML
    private Button retour;


    @FXML
    private TextField rechercherCl;

    @FXML
    private TableView<Client> tabClient;

    @FXML
    private TableColumn<Client, String> TcdCl;

    @FXML
    private TableColumn<Client, String> TnomCl;

    @FXML
    private TableColumn<Client, String> TpreCl;

    @FXML
    private TableColumn<Client, String> TaddCl;

    @FXML
    private TableColumn<Client, String> TconCl;

    private ObservableList<Client> clientList = FXCollections.observableArrayList();

   
    @FXML
    void initialize() {
        // Liaison des colonnes avec les propriétés de l'objet Client
        TcdCl.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        TnomCl.setCellValueFactory(new PropertyValueFactory<>("nomClient"));
        TpreCl.setCellValueFactory(new PropertyValueFactory<>("prenomClient"));
        TaddCl.setCellValueFactory(new PropertyValueFactory<>("adresseClient"));
        TconCl.setCellValueFactory(new PropertyValueFactory<>("contact"));

        // Charger les clients dans la table lors de l'initialisation
        chargerClients();

        // Ajouter un listener pour la sélection dans la table
        tabClient.setOnMouseClicked(event -> onTableClick());
    }


    @FXML
    void ajouterCl(ActionEvent event) {
        String id = GestionClientDao.generateId("CL");
        String nom = nomCl.getText();
        String prenom = prenomCl.getText();
        String adresse = addCl.getText();
        String contact = contactCl.getText();

        if (!GestionClientDao.isValidContact(contact)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Contact invalide. Le numéro doit commencer par '6' et contenir 9 chiffres.", ButtonType.OK);
            alert.show();
            return;
        }

        Client client = new Client(id, nom, prenom, adresse, contact);
        int resultat = GestionClientDao.enregistrer(client);

        if (resultat > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Client ajouté avec succès.", ButtonType.OK);
            alert.show();
            chargerClients(); // Mise à jour de la table
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du client.", ButtonType.OK);
            alert.show();
        }
    }
    
    @FXML
    void onTableClick() {
        Client selectedClient = tabClient.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            remplirFormulaire(selectedClient);
        }
    }

    private void remplirFormulaire(Client client) {
        nomCl.setText(client.getNomClient());
        prenomCl.setText(client.getPrenomClient());
        addCl.setText(client.getAdresseClient());
        contactCl.setText(client.getContact());
    }


    @FXML
    void modifierCl(ActionEvent event) {
        Client selectedClient = tabClient.getSelectionModel().getSelectedItem();
        if (selectedClient == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un client à modifier.", ButtonType.OK);
            alert.show();
            return;
        }

        String nom = nomCl.getText();
        String prenom = prenomCl.getText();
        String adresse = addCl.getText();
        String contact = contactCl.getText();

        if (!GestionClientDao.isValidContact(contact)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Contact invalide. Le numéro doit commencer par '6' et contenir 9 chiffres.", ButtonType.OK);
            alert.show();
            return;
        }

        selectedClient.setNomClient(nom);
        selectedClient.setPrenomClient(prenom);
        selectedClient.setAdresseClient(adresse);
        selectedClient.setContact(contact);

        int resultat = GestionClientDao.modifier(selectedClient);

        if (resultat > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Client modifié avec succès.", ButtonType.OK);
            alert.show();
            chargerClients(); // Mise à jour de la table
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la modification du client.", ButtonType.OK);
            alert.show();
        }
    }

    @FXML
    void rechercherCl(ActionEvent event) {
        String idClient = rechercherCl.getText();
        Client client = GestionClientDao.rechercher(idClient);

        if (client != null && client.getIdClient() != null) {
            clientList.clear();
            clientList.add(client);
            tabClient.setItems(clientList);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun client trouvé pour l'ID : " + idClient, ButtonType.OK);
            alert.show();
        }
    }

    private void chargerClients() {
        clientList.clear();
        clientList.addAll(GestionClientDao.getAllClients());
        tabClient.setItems(clientList);
    }
    
    private void chargerNouvellePage(ActionEvent event, String fxmlFile, String title) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxmlFile));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible de charger la page : " + fxmlFile, ButtonType.OK);
            alert.show();
        }
    }

    
    @FXML
    void retour(ActionEvent event) {
        String view = role.equals("gestionnaire") ? "MenuGestView.fxml" : "AdminMenuView.fxml";
        chargerNouvellePage(event, view, "Menu");
    }


    @FXML
    void seDeconnecter(ActionEvent event) {
        chargerNouvellePage(event, "ConnexionView.fxml", "Connexion");
    }

}
