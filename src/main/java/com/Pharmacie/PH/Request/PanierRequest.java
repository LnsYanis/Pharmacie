package com.Pharmacie.PH.Request;

public class PanierRequest {
   private String   Produit ;
    public String getProduit() {
	return Produit;
}

public void setProduit(String produit) {
	Produit = produit;
}

	@Override
public String toString() {
	return "PanierRequest [Produit=" + Produit + ", clientId=" + clientId + ", quantite=" + quantite + ", Prix="
			+ Prix + "]";
}

	private String clientId;
    private int quantite;
    private long Prix ;

    public long getPrix() {
		return Prix;
	}

	public void setPrix(long prix) {
		Prix = prix;
	}

	public String getclientId() {
        return clientId;
    }

    public void setclientId(String clientId) {
        this.clientId = clientId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
