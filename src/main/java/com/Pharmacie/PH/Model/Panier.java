package com.Pharmacie.PH.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Panier")
public class Panier {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "Nom")
	private String nom;
	@Column(name = "Quantite")
	private long Quantite;
	@Column(name = "Prix")
	private long Prix;
	@Column(name = "prix_total_du_produit")
	private long PrixTotalDuProduit;
	public long getPrixTotalDuProduit() {
		return PrixTotalDuProduit;
	}
	public void setPrixTotalDuProduit(long prixTotalDuProduit) {
		PrixTotalDuProduit = prixTotalDuProduit;
	}
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "client_id")
	private Client client;
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public long getQuantite() {
		return Quantite;
	}
	public void setQuantite(long quantite) {
		Quantite = quantite;
	}
	public long getPrix() {
		return Prix;
	}
	public void setPrix(long prix) {
		Prix = prix;
	}
	public Panier(Long id, String nom, long quantite, long prix) {
		super();
		this.id = id;
		this.nom = nom;
		Quantite = quantite;
		Prix = prix;
	}
	@Override
	public String toString() {
		return "Panier [id=" + id + ", nom=" + nom + ", Quantite=" + Quantite + ", Prix=" + Prix + "]";
	}
public Panier() {};

}
