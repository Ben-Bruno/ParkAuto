package model;

public class Vehicules {
	
    private String idVehicule;
    private String typeVehicule;
    private String marque;
    private String modele;
    private int nombrePlace;
    private boolean disponibilite;
    private double valeur;
    
    
	public Vehicules(String idVehicule, String typeVehicule, String marque, String modele, int nombrePlace,
			boolean disponibilite, double valeur) {
		super();
		this.idVehicule = idVehicule;
		this.typeVehicule = typeVehicule;
		this.marque = marque;
		this.modele = modele;
		this.nombrePlace = nombrePlace;
		this.disponibilite = disponibilite;
		this.valeur = valeur;
	}
	
	
	public String getIdVehicule() {
		return idVehicule;
	}
	public void setIdVehicule(String idVehicule) {
		this.idVehicule = idVehicule;
	}
	public String getTypeVehicule() {
		return typeVehicule;
	}
	public void setTypeVehicule(String typeVehicule) {
		this.typeVehicule = typeVehicule;
	}
	public String getMarque() {
		return marque;
	}
	public void setMarque(String marque) {
		this.marque = marque;
	}
	public String getModele() {
		return modele;
	}
	public void setModele(String modele) {
		this.modele = modele;
	}
	public int getNombrePlace() {
		return nombrePlace;
	}
	public void setNombrePlace(int nombrePlace) {
		this.nombrePlace = nombrePlace;
	}
	public boolean isDisponibilite() {
		return disponibilite;
	}
	public void setDisponibilite(boolean disponibilite) {
		this.disponibilite = disponibilite;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
    
    

}
