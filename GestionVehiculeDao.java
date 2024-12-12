package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import connexion.Mysql;
import model.Vehicules;

public class GestionVehiculeDao {
	
    public static String generateId(String prefix) {
        Random rand = new Random();
        return prefix + String.format("%03d", rand.nextInt(1000));
    }

		public static int enregistrer(Vehicules vh) {
			int et = 0 ;
			Connection conn = null;

			try {
				
				
			String sql ="INSERT INTO vehicule (idVehicule, type, marque, modele, place, dispo, valeur, photo) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
				
				try {
				     Mysql con= new Mysql();

					 conn=con.connect();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PreparedStatement stat;
				stat = conn.prepareStatement(sql);
				stat.setString(1, vh.getIdVehicule());
				stat.setString(2, vh.getType());
				stat.setString(3, vh.getMarque());
				stat.setString(4, vh.getModele());
				stat.setInt(5, vh.getPlace());
				stat.setString(6, vh.getDispo());
				stat.setDouble(7, vh.getValeur());
				stat.setString(8, vh.getPhoto());
				
				
				 et =stat.executeUpdate();
				
				conn.close();
			}catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			
			return et;
		}


		public static int modifier(Vehicules vh) {
			int et = 0 ;
			Connection conn = null;

			try {
				
				
			String sql ="UPDATE vehicule set type = ?, marque = ?, modele = ?, place = ?, dispo = ?,  valeur = ?, photo = ?  WHERE idVehicule = ? ";
				
				try {
				     Mysql con= new Mysql();

					 conn=con.connect();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PreparedStatement stat;
				
				stat = conn.prepareStatement(sql);
				stat.setString(1, vh.getType());
				stat.setString(2, vh.getMarque());
				stat.setString(3, vh.getModele());
				stat.setInt(4, vh.getPlace());
				stat.setString(5, vh.getDispo());
				stat.setDouble(6, vh.getValeur());
				stat.setString(7, vh.getPhoto());
				stat.setString(8, vh.getIdVehicule());
				
				
				
				 et =stat.executeUpdate();
				
				conn.close();
			}catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			
			return et;
		}
		
		public static int supprimer(String idVehicule) {
			int et1=0;
			Connection conn = null;

			try {
			String sql ="DELETE FROM vehicule WHERE idVehicule=?";

			Mysql con= new Mysql();

			conn=con.connect();

				PreparedStatement stat;
				stat = conn.prepareStatement(sql);
				stat.setString(1, idVehicule);
				 et1 =stat.executeUpdate();
				
				conn.close();
			}catch (SQLException e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}

			return et1;

			}
		
		
		public static Vehicules rechercher(String idVehicule) {
			Vehicules vh =  new Vehicules();
			Connection conn = null;

		try {
		String sql ="SELECT * FROM vehicule WHERE idVehicule=?"; 


		Mysql con= new Mysql();

		conn=con.connect();
			PreparedStatement stat;
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, idVehicule);
			ResultSet rs=stat.executeQuery();
			 if(rs.next()) {
				 vh.setIdVehicule(rs.getString(1));
				 vh.setType(rs.getString(2));
				 vh.setMarque(rs.getString(3));
				 vh.setModele(rs.getString(4));
				 vh.setPlace(rs.getInt(5));	
				 vh.setDispo(rs.getString(6));
				 vh.setValeur(rs.getDouble(7));
				 vh.setPhoto(rs.getString(8));
				 
			 }
			
			conn.close();
		}catch (SQLException e2) {
			// TODO: handle exception
			e2.printStackTrace();   
		}

		return vh;
		}
		
		
		public static List<Vehicules> getAllVehicules() {
			// TODO Auto-generated method stub
		        List<Vehicules> vehicules= new ArrayList<>();

		        try (Connection conn = new Mysql().connect()) {
		            String sql = "SELECT * FROM vehicule";

		            try (PreparedStatement stat = conn.prepareStatement(sql);
		                 ResultSet rs = stat.executeQuery()) {

		                while (rs.next()) {
		                    Vehicules vh = new Vehicules();
		                    vh.setIdVehicule(rs.getString("idVehicule"));
		                    vh.setType(rs.getString("type"));
		                    vh.setMarque(rs.getString("marque"));
		                    vh.setModele(rs.getString("modele"));
		                    vh.setPlace(rs.getInt("place"));
		                    vh.setDispo(rs.getString("dispo"));
		                    vh.setValeur(rs.getDouble("valeur"));
		                    vh.setPhoto(rs.getString("photo"));
		                    vehicules.add(vh);
		                }
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }

		        return vehicules;
		    }
		
		
}
	


