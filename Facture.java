package model;

public class Facture {

    private String idFacture;
    private String dateFacture;

    public Facture(String idFacture, String dateFacture) {
        this.idFacture = idFacture;
        this.dateFacture = dateFacture;
    }

	public String getIdFacture() {
		return idFacture;
	}

	public void setIdFacture(String idFacture) {
		this.idFacture = idFacture;
	}

	public String getDateFacture() {
		return dateFacture;
	}

	public void setDateFacture(String dateFacture) {
		this.dateFacture = dateFacture;
	}
    
    
}
