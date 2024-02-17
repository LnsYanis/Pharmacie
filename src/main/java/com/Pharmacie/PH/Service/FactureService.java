package com.Pharmacie.PH.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Model.ClientDTO;
import com.Pharmacie.PH.Model.Facture;
import com.Pharmacie.PH.Model.FactureDTO;
import com.Pharmacie.PH.Repository.ClientRepository;
import com.Pharmacie.PH.Repository.FactureRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class FactureService {

	private final FactureRepository factureRepository;
	private final ClientRepository clientRepository;

	@Autowired
	public FactureService(FactureRepository factureRepository, ClientRepository clientRepository) {
		this.factureRepository = factureRepository;
		this.clientRepository = clientRepository;
	}

	@Transactional
    public Map<String, Object> ajouterFacture(String clientId, double prixTotal, int quantiteTotale) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé avec l'ID : " + clientId));
        System.out.println("ClientId: " + clientId);
        System.out.println("PrixTotal: " + prixTotal);
        System.out.println("QuantiteTotale: " + quantiteTotale);

        Facture facture = new Facture();
        facture.setValeur(prixTotal);
        facture.setQuantites(quantiteTotale);
        facture.setClient(client);

        Facture savedFacture = factureRepository.save(facture);

        Map<String, Object> response = new HashMap<>();
        response.put("factureId", savedFacture.getId());
        response.put("message", "Facture ajoutée avec succès");
        return response;
    }
	public Facture recupererFacture(Long factureId) {
	    return factureRepository.findById(factureId).orElse(null);
	}

	 public FactureDTO convertToDTO(Facture facture) {
	        FactureDTO factureDTO = new FactureDTO();
	        factureDTO.setId(facture.getId());
	        factureDTO.setValeur(facture.getValeur());
	        factureDTO.setQuantites(facture.getQuantites());
	        ClientDTO clientDTO = new ClientDTO();
	        clientDTO.setNom(facture.getClient().getNom());
	        clientDTO.setEmail(facture.getClient().getEmail());
	        clientDTO.setPrenom(facture.getClient().getPrenom());

	        factureDTO.setClient(clientDTO);

	        return factureDTO;
	    }
}
