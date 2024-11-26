package model;

public class Reservation {
    private String idReservation;
    private String dateDebut;
    private String dateExpiration;
    private double coutTotal;
    private String etatReservation;

    public Reservation(String idReservation, String dateDebut, String dateExpiration, double coutTotal, String etatReservation) {
        this.idReservation = idReservation;
        this.dateDebut = dateDebut;
        this.dateExpiration = dateExpiration;
        this.coutTotal = coutTotal;
        this.etatReservation = etatReservation;
    }

	public String getIdReservation() {
		return idReservation;
	}

	public void setIdReservation(String idReservation) {
		this.idReservation = idReservation;
	}

	public String getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}

	public String getDateExpiration() {
		return dateExpiration;
	}

	public void setDateExpiration(String dateExpiration) {
		this.dateExpiration = dateExpiration;
	}

	public double getCoutTotal() {
		return coutTotal;
	}

	public void setCoutTotal(double coutTotal) {
		this.coutTotal = coutTotal;
	}

	public String getEtatReservation() {
		return etatReservation;
	}

	public void setEtatReservation(String etatReservation) {
		this.etatReservation = etatReservation;
	}
    
    
	

}
