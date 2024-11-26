package model;

public class Gestionnaire {
	
	 private String idGestionnaire;
	    private String nomGestionnaire;
	    private String prenomGestionnaire;
	    private String mailGestionnaire;
	    private String mdpGestionnaire;

	    public Gestionnaire(String idGestionnaire, String nomGestionnaire, String prenomGestionnaire, String mailGestionnaire, String mdpGestionnaire) {
	        this.idGestionnaire = idGestionnaire;
	        this.nomGestionnaire = nomGestionnaire;
	        this.prenomGestionnaire = prenomGestionnaire;
	        this.mailGestionnaire = mailGestionnaire;
	        this.mdpGestionnaire = mdpGestionnaire;
	    }


	    public String getIdGestionnaire() {
	        return idGestionnaire;
	    }

	    public void setIdGestionnaire(String idGestionnaire) {
	        this.idGestionnaire = idGestionnaire;
	    }

	    public String getNomGestionnaire() {
	        return nomGestionnaire;
	    }

	    public void setNomGestionnaire(String nomGestionnaire) {
	        this.nomGestionnaire = nomGestionnaire;
	    }

	    public String getPrenomGestionnaire() {
	        return prenomGestionnaire;
	    }

	    public void setPrenomGestionnaire(String prenomGestionnaire) {
	        this.prenomGestionnaire = prenomGestionnaire;
	    }

	    public String getMailGestionnaire() {
	        return mailGestionnaire;
	    }

	    public void setMailGestionnaire(String mailGestionnaire) {
	        this.mailGestionnaire = mailGestionnaire;
	    }

	    public String getMdpGestionnaire() {
	        return mdpGestionnaire;
	    }

	    public void setMdpGestionnaire(String mdpGestionnaire) {
	        this.mdpGestionnaire = mdpGestionnaire;
	    }

}
