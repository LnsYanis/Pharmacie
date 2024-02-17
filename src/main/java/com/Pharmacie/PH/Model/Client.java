package com.Pharmacie.PH.Model;

import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Client")
public class Client {
	 @Id
	    @GeneratedValue(generator = "uuid2")
	    @GenericGenerator(name = "uuid2", strategy = "uuid2")
	    @Column(name = "id")
	    private String id;

    @Column(name = "Nom")
    private String nom;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "email")
    private String email;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "mdp")
    private String mdp;
    
    public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Column(name = "role")  
    private String role;
    
    public List<Panier> getPaniers() {
		return paniers;
	}

	public void setPaniers(List<Panier> paniers) {
		this.paniers = paniers;
	}

	public List<Facture> getFactures() {
		return factures;
	}

	public void setFactures(List<Facture> factures) {
		this.factures = factures;
	}

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Panier> paniers;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    
    private List<Facture> factures;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

   
}
