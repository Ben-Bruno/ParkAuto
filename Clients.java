package model;

public class Clients {
    private String idClient;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private String contactClient;
    
    
    
	public Clients(String idClient, String nomClient, String prenomClient, String adresseClient, String contactClient) {
		super();
		this.idClient = idClient;
		this.nomClient = nomClient;
		this.prenomClient = prenomClient;
		this.adresseClient = adresseClient;
		this.contactClient = contactClient;
	}
	
	
	public String getIdClient() {
		return idClient;
	}
	public void setIdClient(String idClient) {
		this.idClient = idClient;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getPrenomClient() {
		return prenomClient;
	}
	public void setPrenomClient(String prenomClient) {
		this.prenomClient = prenomClient;
	}
	public String getAdresseClient() {
		return adresseClient;
	}
	public void setAdresseClient(String adresseClient) {
		this.adresseClient = adresseClient;
	}
	public String getContactClient() {
		return contactClient;
	}
	public void setContactClient(String contactClient) {
		this.contactClient = contactClient;
	}
    
    

}
