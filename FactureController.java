package controller;

import dao.FactureDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Facture;

import java.io.FileWriter;
import java.io.IOException;

public class FactureController {

    @FXML
    void imprimerFacture(ActionEvent event, String idReservation) {
        Facture facture = FactureDao.obtenirFactureParReservation(idReservation);

        if (facture == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucune facture trouvée pour cette réservation.", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            String cheminFichier = "factures/facture_" + facture.getIdReservation() + ".pdf";
            try (FileWriter writer = new FileWriter(cheminFichier)) {
                writer.write(genererContenuFacture(facture));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Facture générée avec succès : " + cheminFichier, ButtonType.OK);
            alert.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la génération de la facture.", ButtonType.OK);
            alert.show();
            e.printStackTrace();
        }
    }

    private String genererContenuFacture(Facture facture) {
        return """
                -------------------------
                      FACTURE
                -------------------------
                Numéro de réservation : %s
                Client : %s
                Véhicule : %s (%s)
                Coût total : %.2f
                Avance : %.2f
                Reste : %.2f
                -------------------------
                Merci pour votre réservation !
                """.formatted(
                facture.getIdReservation(),
                facture.getNomClient(),
                facture.getMarque(),
                facture.getImmatriculation(),
                facture.getCout(),
                facture.getAvance(),
                facture.getReste()
        );
    }
}
