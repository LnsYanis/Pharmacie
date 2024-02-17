package com.Pharmacie.PH.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Model.Panier;
import com.Pharmacie.PH.Repository.ClientRepository;
import com.Pharmacie.PH.Repository.PanierRepository;
import com.Pharmacie.PH.Request.PanierRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class PanierService {

	private final PanierRepository panierRepository;
	private final ClientRepository clientRepository;

	@Autowired
	public PanierService(PanierRepository panierRepository, ClientRepository clientRepository) {
		this.panierRepository = panierRepository;
		this.clientRepository = clientRepository;
	}

	public Panier createPanier(Panier panier) {
		return panierRepository.save(panier);
	}

	public void ajouterAuPanier(PanierRequest request) {
		String clientId = request.getclientId();

		Optional<Client> clientOptional = clientRepository.findById(clientId);

		if (clientOptional.isPresent()) {
			Client client = clientOptional.get();
			String nomProduit = request.getProduit();
			Optional<Panier> panierOptional = panierRepository.findByClientAndNom(client, nomProduit);

			if (panierOptional.isPresent()) {
				Panier panier = panierOptional.get();
				panier.setQuantite(panier.getQuantite() + request.getQuantite());
				long prixTotalDuProduit = panier.getQuantite() * panier.getPrix();
				panier.setPrixTotalDuProduit(prixTotalDuProduit);
				panierRepository.save(panier);

				System.out.println("Mise à jour du panier réussie.");
			} else {
				Panier nouveauPanier = new Panier();
				nouveauPanier.setClient(client);
				nouveauPanier.setNom(request.getProduit());
				nouveauPanier.setQuantite(request.getQuantite());
				nouveauPanier.setPrix(request.getPrix());
				long prixTotalDuProduit = nouveauPanier.getQuantite() * nouveauPanier.getPrix();
				nouveauPanier.setPrixTotalDuProduit(prixTotalDuProduit);
				panierRepository.save(nouveauPanier);

				System.out.println("Ajout d'un nouveau produit au panier réussi.");
			}
		} else {
			System.err.println("Client non trouvé avec l'ID : " + clientId);
		}
	}
	public List<Panier> getCommandesClient(String clientId) {
		List<Panier> commandes = panierRepository.findByClient_Id(clientId);

		if (commandes.isEmpty()) {
			
			System.out.println("Aucune commande trouvée pour le client avec l'ID : " + clientId);
			return Collections.emptyList(); 
		}

		return commandes;
	}
	@Transactional
	public void supprimerCommande(String clientId, String nomProduit) {
		panierRepository.supprimerCommandeByIdAndNom(clientId, nomProduit);
	}
	@Transactional
    public void supprimerPanier(String clientId) {
        
        panierRepository.deleteByClientId(clientId);
    }


}
