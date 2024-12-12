package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import connexion.Mysql;
import model.Gestionnaire;
import verification.PasswordHacher;

public class GestionGestionnaireDao {

    public static String generateId(String prefix) {
        Random rand = new Random();
        return prefix + String.format("%03d", rand.nextInt(1000));
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static int enregistrer(Gestionnaire gt) {
        int et = 0;

        // Validation de l'adresse email
        if (!isValidEmail(gt.getEmailGestionnaire())) {
            System.out.println("Adresse email invalide.");
            return et;
        }

        // Génération d'un ID unique
        gt.setIdGestionnaire(generateId("GEST"));

        // Hachage du mot de passe
        String hashedPassword = PasswordHacher.hashPassword(gt.getPass());
        if (hashedPassword == null || hashedPassword.length() > 64) {
            System.out.println("Erreur lors du hachage du mot de passe ou longueur excessive.");
            return et;
        }
        gt.setPass(hashedPassword);

        // Définition du rôle par défaut (gestionnaire)
        if (gt.getRole() == null || gt.getRole().isEmpty()) {
            gt.setRole("gestionnaire");
        }

        // Requête SQL pour insérer les données
        String sql = "INSERT INTO gestionnaire (idGestionnaire, nomGestionnaire, prenomGestionnaire, emailGestionnaire, pass, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new Mysql().connect();
             PreparedStatement stat = conn.prepareStatement(sql)) {

            stat.setString(1, gt.getIdGestionnaire());
            stat.setString(2, gt.getNomGestionnaire());
            stat.setString(3, gt.getPrenomGestionnaire());
            stat.setString(4, gt.getEmailGestionnaire());
            stat.setString(5, gt.getPass());
            stat.setString(6, gt.getRole()); // Rôle par défaut ou celui spécifié

            et = stat.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return et;
    }

    public static int modifier(Gestionnaire gt) {
        int et = 0;

        try (Connection conn = new Mysql().connect()) {
            if (!isValidEmail(gt.getEmailGestionnaire())) {
                System.out.println("Adresse email invalide.");
                return et;
            }

            // Hachage du mot de passe avant mise à jour
            String hashedPassword = PasswordHacher.hashPassword(gt.getPass());
            gt.setPass(hashedPassword);

            // Requête SQL de mise à jour
            String sql = "UPDATE gestionnaire SET nomGestionnaire = ?, prenomGestionnaire = ?, emailGestionnaire = ?, pass = ?, role = ? WHERE idGestionnaire = ?";

            try (PreparedStatement stat = conn.prepareStatement(sql)) {
                stat.setString(1, gt.getNomGestionnaire());
                stat.setString(2, gt.getPrenomGestionnaire());
                stat.setString(3, gt.getEmailGestionnaire());
                stat.setString(4, gt.getPass());
                stat.setString(5, gt.getRole());
                stat.setString(6, gt.getIdGestionnaire());

                et = stat.executeUpdate();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return et;
    }

    public static int supprimer(String idGestionnaire) {
        int et = 0;

        try (Connection conn = new Mysql().connect()) {
            String sql = "DELETE FROM gestionnaire WHERE idGestionnaire = ?";

            try (PreparedStatement stat = conn.prepareStatement(sql)) {
                stat.setString(1, idGestionnaire);
                et = stat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return et;
    }

    public static Gestionnaire rechercher(String idGestionnaire) {
        Gestionnaire gt = null;

        try (Connection conn = new Mysql().connect()) {
            String sql = "SELECT * FROM gestionnaire WHERE idGestionnaire = ?";

            try (PreparedStatement stat = conn.prepareStatement(sql)) {
                stat.setString(1, idGestionnaire);
                try (ResultSet rs = stat.executeQuery()) {
                    if (rs.next()) {
                        gt = new Gestionnaire();
                        gt.setIdGestionnaire(rs.getString("idGestionnaire"));
                        gt.setNomGestionnaire(rs.getString("nomGestionnaire"));
                        gt.setPrenomGestionnaire(rs.getString("prenomGestionnaire"));
                        gt.setEmailGestionnaire(rs.getString("emailGestionnaire"));
                        gt.setPass(rs.getString("pass"));
                        gt.setRole(rs.getString("role")); // Récupération du rôle
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gt;
    }

    public static List<Gestionnaire> getAllGestionnaires() {
        List<Gestionnaire> gestionnaires = new ArrayList<>();

        try (Connection conn = new Mysql().connect()) {
            String sql = "SELECT * FROM gestionnaire";

            try (PreparedStatement stat = conn.prepareStatement(sql);
                 ResultSet rs = stat.executeQuery()) {

                while (rs.next()) {
                    Gestionnaire gt = new Gestionnaire();
                    gt.setIdGestionnaire(rs.getString("idGestionnaire"));
                    gt.setNomGestionnaire(rs.getString("nomGestionnaire"));
                    gt.setPrenomGestionnaire(rs.getString("prenomGestionnaire"));
                    gt.setEmailGestionnaire(rs.getString("emailGestionnaire"));
                    gt.setPass(rs.getString("pass"));
                    gt.setRole(rs.getString("role")); // Récupération du rôle

                    gestionnaires.add(gt);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gestionnaires;
    }
}
