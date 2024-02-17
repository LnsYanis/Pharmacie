package com.Pharmacie.PH.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "Produit")
public class Produit {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
    @Column(name = "Nom")
    private String nom;
    @Column(name = "Quantite")
    private long Quantite;
    
    public Produit(Long id, String nom, long quantite, long prix) {
		super();
		this.id = id;
		this.nom = nom;
		Quantite = quantite;
		this.prix = prix;
	}
	public Produit() {};
	@Override
	public String toString() {
		return "Produit [id=" + id + ", nom=" + nom + ", Quantite=" + Quantite + ", prix=" + prix + "]";
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
		return prix;
	}
	public void setPrix(long prix) {
		this.prix = prix;
	}
	@Column(name = "Prix")
    private long prix;
}
