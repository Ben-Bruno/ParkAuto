package controller;

import dao.GestionReservationDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class GestionReservationController {

    @FXML
    private TextField idres;

    @FXML
    private ComboBox<String> cl;

    @FXML
    private ComboBox<String> vh;

    @FXML
    private DatePicker datedebut;

    @FXML
    private DatePicker datefin;

    @FXML
    private TextField debut;

    @FXML
    private TextField fin;

    @FXML
    private DatePicker dateReservation;

    @FXML
    private ComboBox<String> motif;

    @FXML
    private TextField cout;

    @FXML
    private TextField avance;

    @FXML
    private TextField reste;
    
    @FXML
    private Button ajouterRes;

    @FXML
    private Button modifierRes;

    @FXML
    private Button supprimerRes;

    @FXML
    private Button rechercherResBtn;

    @FXML
    private TextField rechercherRes;

    @FXML
    private TableView<Reservation> tabReservation;

    @FXML
    private TableColumn<Reservation, String> TidRes;

    @FXML
    private TableColumn<Reservation, String> TdateDebut;

    @FXML
    private TableColumn<Reservation, String> TdatefinRes;

    @FXML
    private TableColumn<Reservation, Double> TcoutRes;

    @FXML
    private TableColumn<Reservation, Double> Tavance;

    @FXML
    private TableColumn<Reservation, Double> Treste;

    @FXML
    private TableColumn<Reservation, String> Tcl;

    @FXML
    private TableColumn<Reservation, String> Tvh;

    @FXML
    private TableColumn<Reservation, String> TheureDebut;

    @FXML
    private TableColumn<Reservation, String> TheureFin;

    @FXML
    private TableColumn<Reservation, String> Tmotif;

    @FXML
    private TableColumn<Reservation, String> TDateReservation;

    private ObservableList<Reservation> reservations;

    @FXML
    public void initialize() {
        // Initialiser les ComboBox
        vh.setItems(FXCollections.observableArrayList(GestionReservationDao.obtenirIdVehicules()));
        cl.setItems(FXCollections.observableArrayList(GestionReservationDao.obtenirIdClients()));
        motif.setItems(FXCollections.observableArrayList("Mariage", "Transport", "Réunion", "Sortie"));

        // Initialiser les colonnes du tableau
        TidRes.setCellValueFactory(cellData -> cellData.getValue().idReservationProperty());
        TdateDebut.setCellValueFactory(cellData -> cellData.getValue().dateDebutProperty());
        TdatefinRes.setCellValueFactory(cellData -> cellData.getValue().dateFinProperty());
        TcoutRes.setCellValueFactory(cellData -> cellData.getValue().coutProperty().asObject());
        Tavance.setCellValueFactory(cellData -> cellData.getValue().avanceProperty().asObject());
        Treste.setCellValueFactory(cellData -> cellData.getValue().resteProperty().asObject());
        Tcl.setCellValueFactory(cellData -> cellData.getValue().idClientProperty());
        Tvh.setCellValueFactory(cellData -> cellData.getValue().idVehiculeProperty());
        TheureDebut.setCellValueFactory(cellData -> cellData.getValue().heureDebutProperty());
        TheureFin.setCellValueFactory(cellData -> cellData.getValue().heureFinProperty());
        Tmotif.setCellValueFactory(cellData -> cellData.getValue().motifProperty());
        TDateReservation.setCellValueFactory(cellData -> cellData.getValue().dateReservationProperty());

        // Charger les réservations initiales
        loadReservations();

        // Ajouter des listeners pour les calculs automatiques
        ajouterListeners();
    }

    private void ajouterListeners() {
        vh.setOnAction(event -> calculerCout());
        datedebut.setOnAction(event -> calculerCout());
        datefin.setOnAction(event -> calculerCout());
        debut.setOnKeyReleased(event -> calculerCout());
        fin.setOnKeyReleased(event -> calculerCout());
        avance.setOnKeyReleased(event -> calculerReste());
    }

    private void loadReservations() {
        reservations = FXCollections.observableArrayList(GestionReservationDao.rechercher(""));
        tabReservation.setItems(reservations);
    }

    private void calculerCout() {
        try {
            String vehiculeId = vh.getValue();
            LocalDate dateDebut = datedebut.getValue();
            LocalDate dateFin = datefin.getValue();
            LocalTime heureDebut = LocalTime.parse(debut.getText());
            LocalTime heureFin = LocalTime.parse(fin.getText());

            if (vehiculeId != null && dateDebut != null && dateFin != null && heureDebut != null && heureFin != null) {
                double prixHoraire = GestionReservationDao.obtenirPrixHoraireVehicule(vehiculeId);
                long heuresTotales = ChronoUnit.HOURS.between(heureDebut, heureFin) +
                        ChronoUnit.DAYS.between(dateDebut, dateFin) * 24;
                cout.setText(String.valueOf(prixHoraire * heuresTotales));
            }
        } catch (Exception e) {
            cout.clear();
        }
    }

    private void calculerReste() {
        try {
            double coutValue = Double.parseDouble(cout.getText());
            double avanceValue = Double.parseDouble(avance.getText());
            reste.setText(String.valueOf(coutValue - avanceValue));
        } catch (Exception e) {
            reste.clear();
        }
    }

    @FXML
    void ajouterRes(ActionEvent event) {
        try {
            // Formater et valider les heures
            String heureDebut = formatHeure(debut.getText());
            String heureFin = formatHeure(fin.getText());

            // Calcul du reste
            double coutValue = Double.parseDouble(cout.getText());
            double avanceValue = Double.parseDouble(avance.getText());
            double resteValue = coutValue - avanceValue;

            // Créer l'objet Reservation
            Reservation res = new Reservation(
                    idres.getText(),
                    vh.getValue(),
                    cl.getValue(),
                    LocalDate.now().toString(), // Date de réservation = Date système
                    datedebut.getValue().toString(),
                    datefin.getValue().toString(),
                    heureDebut,
                    heureFin,
                    motif.getValue(),
                    coutValue,
                    avanceValue,
                    resteValue
            );

            // Insérer dans la base de données
            GestionReservationDao.enregistrer(res);

            // Actualiser la TableView
            loadReservations();

            // Réinitialiser le formulaire
            clearForm();
            

            showSuccess("Réservation ajoutée avec succès !");
        } catch (Exception e) {
            showError("Erreur lors de l'ajout : " + e.getMessage());
        }
    }



    private Reservation getReservationFromForm() {
        String id = idres.getText();
        String client = cl.getValue();
        String vehicule = vh.getValue();
        String dateRes = LocalDate.now().toString();
        String dateDebut = datedebut.getValue().toString();
        String dateFin = datefin.getValue().toString();
        String heureDebut = debut.getText();
        String heureFin = fin.getText();
        String motifValue = motif.getValue();
        double coutValue = Double.parseDouble(cout.getText());
        double avanceValue = Double.parseDouble(avance.getText());
        double resteValue = Double.parseDouble(reste.getText());

        return new Reservation(id, vehicule, client, dateRes, dateDebut, dateFin, heureDebut, heureFin, motifValue, coutValue, avanceValue, resteValue);
    }

    private void clearForm() {
        idres.clear();
        cl.setValue(null);
        vh.setValue(null);
        datedebut.setValue(null);
        datefin.setValue(null);
        debut.clear();
        fin.clear();
        motif.setValue(null);
        cout.clear();
        avance.clear();
        reste.clear();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Opération échouée");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText("Opération réussie");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    


    @FXML
    void onTableClick() {
        Reservation selectedReservation = tabReservation.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            setFormFromReservation(selectedReservation);
        }
    }

    private void setFormFromReservation(Reservation res) {
        idres.setText(res.getIdReservation());
        cl.setValue(res.getIdClient());
        vh.setValue(res.getIdVehicule());
        datedebut.setValue(LocalDate.parse(res.getDateDebut()));
        datefin.setValue(LocalDate.parse(res.getDateFin()));
        debut.setText(res.getHeureDebut());
        fin.setText(res.getHeureFin());
        motif.setValue(res.getMotif());
        cout.setText(String.valueOf(res.getCout()));
        avance.setText(String.valueOf(res.getAvance()));
        reste.setText(String.valueOf(res.getReste()));
    }

    @FXML
    void modifierRes(ActionEvent event) {
        try {
            Reservation updatedReservation = getReservationFromForm(); // Récupère les données du formulaire
            if (updatedReservation.getIdReservation() == null || updatedReservation.getIdReservation().isEmpty()) {
                showError("Aucune réservation sélectionnée pour la modification.");
                return;
            }

            GestionReservationDao.modifier(updatedReservation);
            showSuccess("Réservation modifiée avec succès !");
            loadReservations(); // Recharger la table
            clearForm();
        } catch (Exception e) {
            showError("Erreur : " + e.getMessage());
        }
    }


    @FXML
    void supprimerRes(ActionEvent event) {
        try {
            Reservation selected = tabReservation.getSelectionModel().getSelectedItem();
            if (selected == null) {
                throw new IllegalArgumentException("Veuillez sélectionner une réservation à supprimer.");
            }
            LocalDate dateDebutValue = LocalDate.parse(selected.getDateDebut());
            if (GestionReservationDao.isSuppressionAllowed(dateDebutValue)) {
                GestionReservationDao.supprimer(selected.getIdReservation(), dateDebutValue);
                clearForm();
                loadReservations(); // Recharger les données dans la table
            } else {
                showError("Impossible de supprimer la réservation le jour du début.");
            }
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    @FXML
    void rechercherRes(ActionEvent event) {
        try {
            String keyword = rechercherRes.getText().trim();
            ObservableList<Reservation> filteredReservations;
            if (keyword.isEmpty()) {
                filteredReservations = FXCollections.observableArrayList(GestionReservationDao.rechercher(""));
            } else {
                filteredReservations = FXCollections.observableArrayList(GestionReservationDao.rechercher(keyword));
            }
            tabReservation.setItems(filteredReservations);
        } catch (Exception e) {
            showError("Une erreur est survenue lors de la recherche.");
        }
    }
    
    private String formatHeure(String heure) {
        if (heure == null || heure.trim().isEmpty()) {
            throw new IllegalArgumentException("Veuillez saisir une heure valide.");
        }

        if (heure.matches("^\\d{2}:\\d{2}$")) {
            return heure + ":00"; // Ajouter les secondes si non fournies
        }

        if (heure.matches("^\\d{2}:\\d{2}:\\d{2}$")) {
            return heure;
        }

        throw new IllegalArgumentException("Le format de l'heure doit être HH:mm ou HH:mm:ss.");
    }
    





    
}
