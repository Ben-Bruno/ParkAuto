package controller;

import dao.GestionGestionnaireDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Gestionnaire;

public class GestionGestionnaireController {

    @FXML
    private TextField idgest;

    @FXML
    private TextField nomGest;

    @FXML
    private TextField prenomgest;

    @FXML
    private TextField emailgest;

    @FXML
    private PasswordField passgest;
    
    

    @FXML
    private Button ajouterGest;

    @FXML
    private Button modifierGest;

    @FXML
    private Button supprimerGest;

    @FXML
    private Button rechercherGestbtn;

    @FXML
    private TextField rechercherGest;

    @FXML
    private TableView<Gestionnaire> tabVehicule;

    @FXML
    private TableColumn<Gestionnaire, String> TidGest;

    @FXML
    private TableColumn<Gestionnaire, String> TnomGest;

    @FXML
    private TableColumn<Gestionnaire, String> TprenomGest;

    @FXML
    private TableColumn<Gestionnaire, String> TemailGest;

    @FXML
    private TableColumn<Gestionnaire, String> TpassGest;

    @FXML
    private TableColumn<Gestionnaire, String> TroleGest;

    private ObservableList<Gestionnaire> gestionnaireList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Liaison des colonnes avec les propriétés de l'objet Gestionnaire
        TidGest.setCellValueFactory(new PropertyValueFactory<>("idGestionnaire"));
        TnomGest.setCellValueFactory(new PropertyValueFactory<>("nomGestionnaire"));
        TprenomGest.setCellValueFactory(new PropertyValueFactory<>("prenomGestionnaire"));
        TemailGest.setCellValueFactory(new PropertyValueFactory<>("emailGestionnaire"));
        TpassGest.setCellValueFactory(new PropertyValueFactory<>("pass"));
        TroleGest.setCellValueFactory(new PropertyValueFactory<>("role")); // Utilise le getter `getRole`

        // Chargement initial des données
        loadGestionnaires();

        // Synchronisation des champs avec la sélection dans le tableau
        tabVehicule.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateForm(newValue);
            }
        });
    }


    @FXML
    void ajouterGest(ActionEvent event) {
        Gestionnaire gt = new Gestionnaire();
        gt.setNomGestionnaire(nomGest.getText());
        gt.setPrenomGestionnaire(prenomgest.getText());
        gt.setEmailGestionnaire(emailgest.getText());
        gt.setPass(passgest.getText());
        gt.setRole("gestionnaire"); // Rôle par défaut

        int result = GestionGestionnaireDao.enregistrer(gt);
        if (result > 0) {
            // Ajouter directement à la liste observable
            gestionnaireList.add(gt);
            tabVehicule.setItems(gestionnaireList); // Mise à jour de la table
            clearFields();
            showAlert("Succès", "Gestionnaire ajouté avec succès !");
        } else {
            showAlert("Erreur", "Échec de l'ajout du gestionnaire.");
        }
    }

    @FXML
    void modifierGest(ActionEvent event) {
        Gestionnaire selected = tabVehicule.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setNomGestionnaire(nomGest.getText());
            selected.setPrenomGestionnaire(prenomgest.getText());
            selected.setEmailGestionnaire(emailgest.getText());
            selected.setPass(passgest.getText());

            if (GestionGestionnaireDao.modifier(selected) > 0) {
                loadGestionnaires(); // Recharge les données dans la liste observable
                clearFields();
                showAlert("Succès", "Gestionnaire modifié avec succès !");
            } else {
                showAlert("Erreur", "Échec de la modification du gestionnaire.");
            }
        } else {
            showAlert("Erreur", "Aucun gestionnaire sélectionné.");
        }
    }


    @FXML
    void supprimerGest(ActionEvent event) {
        Gestionnaire selected = tabVehicule.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous vraiment supprimer ce gestionnaire ?", ButtonType.YES, ButtonType.NO);
            confirmation.setTitle("Confirmation de suppression");
            confirmation.showAndWait();

            if (confirmation.getResult() == ButtonType.YES) {
                if (GestionGestionnaireDao.supprimer(selected.getIdGestionnaire()) > 0) {
                    gestionnaireList.remove(selected); // Supprime l'élément de la liste
                    tabVehicule.setItems(gestionnaireList); // Actualise la table
                    clearFields();
                    showAlert("Succès", "Gestionnaire supprimé avec succès !");
                } else {
                    showAlert("Erreur", "Échec de la suppression du gestionnaire.");
                }
            }
        } else {
            showAlert("Erreur", "Aucun gestionnaire sélectionné.");
        }
    }

    @FXML
    void rechercherGest(ActionEvent event) {
        String id = rechercherGest.getText().trim();
        gestionnaireList.clear();

        if (!id.isEmpty()) {
            Gestionnaire gt = GestionGestionnaireDao.rechercher(id);
            if (gt != null && gt.getIdGestionnaire() != null) {
                gestionnaireList.add(gt);
            } else {
                showAlert("Erreur", "Gestionnaire introuvable.");
            }
        } else {
            loadGestionnaires();
        }

        tabVehicule.setItems(gestionnaireList);
    }

    private void loadGestionnaires() {
        gestionnaireList.setAll(GestionGestionnaireDao.getAllGestionnaires());
        tabVehicule.setItems(gestionnaireList);
    }


    private void populateForm(Gestionnaire gestionnaire) {
        nomGest.setText(gestionnaire.getNomGestionnaire());
        prenomgest.setText(gestionnaire.getPrenomGestionnaire());
        emailgest.setText(gestionnaire.getEmailGestionnaire());
        passgest.setText(gestionnaire.getPass());
    }

    private void clearFields() {
        if (idgest != null) {
            idgest.clear();
        }

        nomGest.clear();
        prenomgest.clear();
        emailgest.clear();
        passgest.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
