package controller;

import java.io.File;

import dao.GestionVehiculeDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import model.Vehicules;

public class GestionVehiculeController {

    @FXML
    private TextField idVh;

    @FXML
    private ComboBox<String> type;

    @FXML
    private TextField marque;

    @FXML
    private TextField modele;

    @FXML
    private ComboBox<String> disponibilite;

    @FXML
    private TextField place;

    @FXML
    private TextField valeur;
    

    @FXML
    private HBox idvehPhoto;
    

    @FXML
    private TextField photo;
    

    @FXML
    private Button choisirPhoto;

    @FXML
    private Button ajouterVh;

    @FXML
    private Button modifierVh;

    @FXML
    private Button supprimerVh;

    @FXML
    private Button rechercherVhe;

    @FXML
    private TextField rechercherVehicule;

    @FXML
    private TableView<Vehicules> tabVehicule;

    @FXML
    private TableColumn<Vehicules, String> Tidvh;

    @FXML
    private TableColumn<Vehicules, String> Ttype;

    @FXML
    private TableColumn<Vehicules, String> Tmarque;

    @FXML
    private TableColumn<Vehicules, String> Tmodele;

    @FXML
    private TableColumn<Vehicules, String> Tdisponibilite;

    @FXML
    private TableColumn<Vehicules, Integer> Tplace;

    @FXML
    private TableColumn<Vehicules, Double> Tvaleur;

    @FXML
    private TableColumn<Vehicules, String> Tphoto;

    private ObservableList<Vehicules> vehiculesList = FXCollections.observableArrayList();
    
    @FXML
    void choisirPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une photo");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg", "*.gif")
        );

        // Ouvrir la boîte de dialogue de fichier
        File selectedFile = fileChooser.showOpenDialog(choisirPhoto.getScene().getWindow());
        if (selectedFile != null) {
            photo.setText(selectedFile.getAbsolutePath());
        }
    }


		
		    @FXML
		    public void initialize() {
		        // Initialisation des ComboBox
		        type.setItems(FXCollections.observableArrayList("moto", "voiture"));
		        disponibilite.setItems(FXCollections.observableArrayList("oui", "non"));
		
		        // Configuration des colonnes du TableView
		        Tidvh.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
		        Ttype.setCellValueFactory(new PropertyValueFactory<>("type"));
		        Tmarque.setCellValueFactory(new PropertyValueFactory<>("marque"));
		        Tmodele.setCellValueFactory(new PropertyValueFactory<>("modele"));
		        Tdisponibilite.setCellValueFactory(new PropertyValueFactory<>("dispo"));
		        Tplace.setCellValueFactory(new PropertyValueFactory<>("place"));
		        Tvaleur.setCellValueFactory(new PropertyValueFactory<>("valeur"));
		        Tphoto.setCellValueFactory(new PropertyValueFactory<>("photo"));
		
		        // Cellule personnalisée pour afficher les photos
		        Tphoto.setCellFactory(column -> new TableCell<Vehicules, String>() {
		            private final ImageView imageView = new ImageView();
		
		            {
		                imageView.setFitWidth(100);
		                imageView.setFitHeight(100);
		                imageView.setPreserveRatio(true);
		            }
		
		            @Override
		            protected void updateItem(String photoPath, boolean empty) {
		                super.updateItem(photoPath, empty);
		                if (empty || photoPath == null || photoPath.isEmpty()) {
		                    setGraphic(null);
		                } else {
		                    imageView.setImage(new Image("file:" + photoPath, true));
		                    setGraphic(imageView);
		                }
		            }
		        });
		
		        // Charger les données initiales
		        vehiculesList.setAll(GestionVehiculeDao.getAllVehicules());
		        tabVehicule.setItems(vehiculesList);
		
		        // Ajouter un écouteur sur la sélection de ligne dans le TableView
		        tabVehicule.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
		            if (newValue != null) {
		                remplirFormulaire(newValue);
		            }
		        });
		    }
    

			private void remplirFormulaire(Vehicules vehicule) {
			    idVh.setText(vehicule.getIdVehicule());
			    type.setValue(vehicule.getType());
			    marque.setText(vehicule.getMarque());
			    modele.setText(vehicule.getModele());
			    disponibilite.setValue(vehicule.getDispo());
			    place.setText(String.valueOf(vehicule.getPlace()));
			    valeur.setText(String.valueOf(vehicule.getValeur()));
			    photo.setText(vehicule.getPhoto());
			}
    
    
    
    
		    private boolean validateFields() {
		        if (type.getValue() == null || marque.getText().isEmpty() || modele.getText().isEmpty() || 
		            place.getText().isEmpty() || disponibilite.getValue() == null || valeur.getText().isEmpty()) {
		            showAlert(Alert.AlertType.WARNING, "Champs manquants", "Veuillez remplir tous les champs obligatoires.");
		            return false;
		        }
		        try {
		            Integer.parseInt(place.getText());
		            Double.parseDouble(valeur.getText());
		        } catch (NumberFormatException e) {
		            showAlert(Alert.AlertType.ERROR, "Format invalide", "Veuillez saisir des nombres valides pour les places et la valeur.");
		            return false;
		        }
		        return true;
		    }


         
    

    @FXML
    void ajouterVh(ActionEvent event) {
        if (!validateFields()) return;

        String id = GestionVehiculeDao.generateId("VH");
        String typeVehicule = type.getValue();
        String marqueVehicule = marque.getText();
        String modeleVehicule = modele.getText();
        int places = Integer.parseInt(place.getText());
        String dispo = disponibilite.getValue();
        double valeurVehicule = Double.parseDouble(valeur.getText());
        String cheminPhoto = photo.getText();

        Vehicules vehicule = new Vehicules(id, typeVehicule, marqueVehicule, modeleVehicule, places, dispo, valeurVehicule, cheminPhoto);
        if (GestionVehiculeDao.enregistrer(vehicule) > 0) {
            vehiculesList.add(vehicule);
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Véhicule ajouté avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du véhicule.");
        }
    }



    @FXML
    void modifierVh(ActionEvent event) {
        Vehicules vh = tabVehicule.getSelectionModel().getSelectedItem();
        if (vh ==                                         null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un véhicule à modifier.");
            return;
        }

        vh.setType(type.getValue());
        vh.setMarque(marque.getText());
        vh.setModele(modele.getText());
        vh.setPlace(Integer.parseInt(place.getText()));
        vh.setDispo(disponibilite.getValue());
        vh.setValeur(Double.parseDouble(valeur.getText()));
        vh.setPhoto(photo.getText());

        if (GestionVehiculeDao.modifier(vh) > 0) {
            tabVehicule.refresh();
            clearForm();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Véhicule modifié avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la modification du véhicule.");
        }
    }


		@FXML
		void supprimerVh(ActionEvent event) {
		    Vehicules vh = tabVehicule.getSelectionModel().getSelectedItem();
		    if (vh == null) {
		        showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner un véhicule à supprimer.");
		        return;
		    }
		
		    // Boîte de dialogue de confirmation
		    Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
		    confirmationAlert.setTitle("Confirmation");
		    confirmationAlert.setHeaderText("Confirmation de suppression");
		    confirmationAlert.setContentText("Voulez-vous vraiment supprimer ce véhicule ?");
		    ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);
		
		    if (result == ButtonType.OK) {
		        if (GestionVehiculeDao.supprimer(vh.getIdVehicule()) > 0) {
		            vehiculesList.remove(vh);
		            showAlert(Alert.AlertType.INFORMATION, "Succès", "Véhicule supprimé avec succès !");
		        } else {
		            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de la suppression du véhicule.");
		        }
		    }
		}

    @FXML
    void rechercherVehicule(ActionEvent event) {
        String id = idVh.getText();
        Vehicules vh = GestionVehiculeDao.rechercher(id);
        if (vh.getIdVehicule() != null) {
            vehiculesList.clear();
            vehiculesList.add(vh);
        } else {
            showAlert(Alert.AlertType.INFORMATION, "Information", "Aucun véhicule trouvé avec cet ID.");
        }
    }

    private void clearForm() {
        idVh.clear();
        type.setValue(null);
        marque.clear();
        modele.clear();
        disponibilite.setValue(null);
        place.clear();
        valeur.clear();
        photo.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
