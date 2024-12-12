package dao;

import connexion.Mysql;
import model.Facture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactureDao {

    public static Facture obtenirFactureParReservation(String idReservation) {
        Facture facture = null;
        String sql = """
                SELECT r.idReservation, c.nomClient, c.prenomClient, v.immatriculation, v.marque, r.cout, r.avance, r.reste
                FROM reservation r
                JOIN client c ON r.idClient = c.idClient
                JOIN vehicule v ON r.idVehicule = v.idVehicule
                WHERE r.idReservation = ?
                """;

        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement(sql)) {

            stat.setString(1, idReservation);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    facture = new Facture(
                            rs.getString("idReservation"),
                            rs.getString("nomClient") + " " + rs.getString("prenomClient"),
                            rs.getString("immatriculation"),
                            rs.getString("marque"),
                            rs.getDouble("cout"),
                            rs.getDouble("avance"),
                            rs.getDouble("reste")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facture;
    }
}
