package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import connexion.Mysql;
import model.Client;


public class GestionClientDao {
	
    public static String generateId(String prefix) {
        Random rand = new Random();
        return prefix + String.format("%03d", rand.nextInt(1000));
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
    
    public static boolean isValidContact(String contact) {
        // Contact peut inclure un indicatif du pays optionnel (+XXX)
        String contactRegex = "^(\\+\\d{1,3})?6\\d{8}$";
        return contact.matches(contactRegex);
    }

	

	public static int enregistrer(Client cl) {
		int et = 0 ;
		Connection conn = null;

		try {
			
			
		String sql ="INSERT INTO client (idClient, nomClient, prenomClient, adresseClient, contact) VALUES (?, ?, ?, ?, ?) ";
			
			try {
			     Mysql con= new Mysql();

				 conn=con.connect();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			stat.setString(1, cl.getIdClient());
			stat.setString(2, cl.getNomClient());
			stat.setString(3, cl.getPrenomClient());
			stat.setString(4, cl.getAdresseClient());
			stat.setString(5, cl.getContact());
			
			
			 et =stat.executeUpdate();
			
			conn.close();
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
		
		return et;
	}
	
	public static int modifier(Client cl) {
		int et = 0 ;
		Connection conn = null;

		try {
			
			
		String sql ="UPDATE client set nomClient = ?, prenomClient = ?, adresseClient = ?, contact = ? WHERE idClient = ? ";
			
			try {
			     Mysql con= new Mysql();

				 conn=con.connect();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			stat.setString(1, cl.getNomClient());
			stat.setString(2, cl.getPrenomClient());
			stat.setString(3, cl.getAdresseClient());
			stat.setString(4, cl.getContact());
			stat.setString(5, cl.getIdClient());
			
			
			 et =stat.executeUpdate();
			
			conn.close();
		}catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
		
		return et;
	}
	
	
	public static int supprimer(String idClient) {
		int et1=0;
		Connection conn = null;

		try {
		String sql ="DELETE FROM client WHERE idClient=?";

		Mysql con= new Mysql();

		conn=con.connect();

			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			stat.setString(1, idClient);
			 et1 =stat.executeUpdate();
			
			conn.close();
		}catch (SQLException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}

		return et1;

		}
	
	
	public static Client rechercher(String idClient) {
		Client cl =  new Client();
		Connection conn = null;

	try {
	String sql ="SELECT * FROM client WHERE idClient=?"; 


	Mysql con= new Mysql();

	conn=con.connect();
		PreparedStatement stat;
		stat = conn.prepareStatement(sql);
		
		stat.setString(1, idClient);
		ResultSet rs=stat.executeQuery();
		 if(rs.next()) {
			 cl.setIdClient(rs.getString(1));
			 cl.setNomClient(rs.getString(2));
			 cl.setPrenomClient(rs.getString(3));
			 cl.setAdresseClient(rs.getString(4));
			 cl.setContact(rs.getString(5));
			 
			 
		 }
		
		conn.close();
	}catch (SQLException e2) {
		// TODO: handle exception
		e2.printStackTrace();   
	}

	return cl;
	}



	public static List<Client> getAllClients() {
		// TODO Auto-generated method stub
	        List<Client> clients = new ArrayList<>();

	        try (Connection conn = new Mysql().connect()) {
	            String sql = "SELECT * FROM client";

	            try (PreparedStatement stat = conn.prepareStatement(sql);
	                 ResultSet rs = stat.executeQuery()) {

	                while (rs.next()) {
	                    Client cl = new Client();
	                    cl.setIdClient(rs.getString("idClient"));
	                    cl.setNomClient(rs.getString("nomClient"));
	                    cl.setPrenomClient(rs.getString("prenomClient"));
	                    cl.setAdresseClient(rs.getString("adresseClient"));
	                    cl.setContact(rs.getString("contact"));
	                    clients.add(cl);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return clients;
	    }
	}


