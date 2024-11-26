package model;

public class Admin extends Gestionnaire {
    private String idAdmin;

    public Admin(String idAdmin, String idGestionnaire, String nomGestionnaire, String prenomGestionnaire, String mailGestionnaire, String mdpGestionnaire) {
        super(idGestionnaire, nomGestionnaire, prenomGestionnaire, mailGestionnaire, mdpGestionnaire);
        this.idAdmin = idAdmin;
    }

	public String getIdAdmin() {
		return idAdmin;
	}

	public void setIdAdmin(String idAdmin) {
		this.idAdmin = idAdmin;
	}
    

}
