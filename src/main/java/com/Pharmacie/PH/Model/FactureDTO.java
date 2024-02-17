package com.Pharmacie.PH.Model;

public class FactureDTO {
    private Long id;
    private double valeur;
    private ClientDTO client;
    private Integer quantites;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	public ClientDTO getClient() {
		return client;
	}
	public void setClient(ClientDTO client) {
		this.client = client;
	}
	public Integer getQuantites() {
		return quantites;
	}
	public void setQuantites(Integer quantites) {
		this.quantites = quantites;
	}
	public FactureDTO(Long id, double valeur, ClientDTO client, Integer quantites) {
		super();
		this.id = id;
		this.valeur = valeur;
		this.client = client;
		this.quantites = quantites;
	}
	public  FactureDTO() {};
    
}
