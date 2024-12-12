package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import connexion.Mysql;
import model.Reservation;

public class GestionReservationDao {

    public static int enregistrer(Reservation res) {
        try {
            res.setIdReservation(genererIdentifiant());
            validateReservation(res);
            double coutApresReduction = appliquerReduction(res.getCout());
            res.setCout(coutApresReduction);
            
            String sql = "INSERT INTO reservation (idReservation, idVehicule, idClient, dateReservation, dateDebut, dateFin, heureDebut, heureFin, motif, cout, avance, reste) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            return executeUpdate(sql, res);
        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int modifier(Reservation res) {
        try {
            validateReservation(res);
            double coutApresReduction = appliquerReduction(res.getCout());
            res.setCout(coutApresReduction);

            String sql = "UPDATE reservation SET idVehicule = ?, idClient = ?, dateReservation = ?, dateDebut = ?, dateFin = ?, heureDebut = ?, heureFin = ?, motif = ?, cout = ?, avance = ?, reste = ? "
                    + "WHERE idReservation = ?";
            return executeUpdate(sql, res);
        } catch (IllegalArgumentException | SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int supprimer(String idRes, LocalDate dateDebut) {
        try {
            if (!isSuppressionAllowed(dateDebut)) {
                throw new IllegalArgumentException("Impossible de supprimer une réservation le jour du début.");
            }
            String sql = "DELETE FROM reservation WHERE idReservation = ?";
            try (Connection conn = new Mysql().connect();
                 PreparedStatement stat = conn.prepareStatement(sql)) {
                stat.setString(1, idRes);
                return stat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<Reservation> rechercher(String keyword) {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement("SELECT * FROM reservation WHERE idReservation LIKE ? OR idClient LIKE ? OR idVehicule LIKE ?")) {
            stat.setString(1, "%" + keyword + "%");
            stat.setString(2, "%" + keyword + "%");
            stat.setString(3, "%" + keyword + "%");
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    Reservation res = new Reservation(
                            rs.getString("idReservation"),
                            rs.getString("idVehicule"),
                            rs.getString("idClient"),
                            rs.getDate("dateReservation").toString(),
                            rs.getDate("dateDebut").toString(),
                            rs.getDate("dateFin").toString(),
                            rs.getTime("heureDebut").toString(),
                            rs.getTime("heureFin").toString(),
                            rs.getString("motif"),
                            rs.getDouble("cout"),
                            rs.getDouble("avance"),
                            rs.getDouble("reste")
                    );
                    reservations.add(res);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    private static void validateReservation(Reservation res) {
        LocalDate dateReservation = LocalDate.parse(res.getDateReservation());
        LocalDate dateDebut = LocalDate.parse(res.getDateDebut());
        LocalDate dateFin = LocalDate.parse(res.getDateFin());

        if (!isDateReservationValid(dateReservation, dateDebut)) {
            throw new IllegalArgumentException("L'intervalle entre la date de réservation et la date de début doit être de 2 semaines maximum.");
        }

        if (!isDatesValid(dateDebut, dateFin)) {
            throw new IllegalArgumentException("La date de début doit être antérieure ou égale à la date de fin.");
        }

        if (!isAvanceValid(res.getCout(), res.getAvance())) {
            throw new IllegalArgumentException("L'avance doit être au moins 50% du coût de la réservation.");
        }
    }

    private static boolean isDateReservationValid(LocalDate dateReservation, LocalDate dateDebut) {
        return dateDebut.isAfter(dateReservation) && dateDebut.minusWeeks(2).isBefore(dateReservation);
    }

    private static boolean isDatesValid(LocalDate dateDebut, LocalDate dateFin) {
        return !dateDebut.isAfter(dateFin);
    }

    private static boolean isAvanceValid(double cout, double avance) {
        return avance >= (cout * 0.5);
    }

    public static boolean isSuppressionAllowed(LocalDate dateDebut) {
        return dateDebut.isAfter(LocalDate.now());
    }

    private static double appliquerReduction(double cout) {
        if (cout >= 200000) {
            return cout * 0.9;
        } else if (cout >= 100000) {
            return cout * 0.95;
        }
        return cout;
    }

    private static String genererIdentifiant() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private static int executeUpdate(String sql, Reservation res) throws SQLException {
        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, res.getIdReservation());
            stat.setString(2, res.getIdVehicule());
            stat.setString(3, res.getIdClient());
            stat.setDate(4, java.sql.Date.valueOf(res.getDateReservation()));
            stat.setDate(5, java.sql.Date.valueOf(res.getDateDebut()));
            stat.setDate(6, java.sql.Date.valueOf(res.getDateFin()));
            stat.setTime(7, java.sql.Time.valueOf(res.getHeureDebut())); // Assurez-vous que le format est correct
            stat.setTime(8, java.sql.Time.valueOf(res.getHeureFin()));   // Assurez-vous que le format est correct
            stat.setString(9, res.getMotif());
            stat.setDouble(10, res.getCout());
            stat.setDouble(11, res.getAvance());
            stat.setDouble(12, res.getCout() - res.getAvance());
            return stat.executeUpdate();
        }
    }


    

    

    public static List<String> obtenirIdClients() {
        List<String> ids = new ArrayList<>();
        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement("SELECT idClient FROM client");
             ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getString("idClient"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public static List<String> obtenirIdVehicules() {
        List<String> ids = new ArrayList<>();
        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement("SELECT idVehicule FROM vehicule");
             ResultSet rs = stat.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getString("idVehicule"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }
    
    public static double obtenirPrixHoraireVehicule(String idVehicule) {
        double valeur = 0;
        String sql = "SELECT valeur FROM vehicule WHERE idVehicule = ?";
        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement(sql)) {
            stat.setString(1, idVehicule);
            try (ResultSet rs = stat.executeQuery()) {
                if (rs.next()) {
                    valeur = rs.getDouble("valeur");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valeur;
    }
}
