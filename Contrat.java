package model;

public class Contrat {
	
	private String idContrat;
    private String dateContrat;

    public Contrat(String idContrat, String dateContrat) {
        this.idContrat = idContrat;
        this.dateContrat = dateContrat;
    }

	public String getIdContrat() {
		return idContrat;
	}

	public void setIdContrat(String idContrat) {
		this.idContrat = idContrat;
	}

	public String getDateContrat() {
		return dateContrat;
	}

	public void setDateContrat(String dateContrat) {
		this.dateContrat = dateContrat;
	}
    
    

}
