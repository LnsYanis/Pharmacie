package com.Pharmacie.PH.Model;





import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "Facture")
public class Facture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
    @Column(name = "valeur")
    private double valeur;

    @ManyToOne
    @JoinColumn(name = "client_id")  
    private Client client;


    @Column(name = "quantites")
    private Integer quantites;

    
    public Facture() {}

    public Facture(double valeur, Client client, Integer quantites) {
        this.valeur = valeur;
        this.client = client;
        this.quantites = quantites;
    }

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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Integer getQuantites() {
		return quantites;
	}

	public void setQuantites(Integer quantites) {
		this.quantites = quantites;
	}
}
