package com.Pharmacie.PH.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacie.PH.Model.Client;
import com.Pharmacie.PH.Repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> existeNomUtilisateur(String nomUtilisateur) {
        return clientRepository.findByNom(nomUtilisateur);
    }

    public Optional<Client> verifierConnexion(String nomUtilisateur, String mdp) {
        return clientRepository.findByEmailAndMdp(nomUtilisateur, mdp);
    }
   
    public String inscrire(Client client) {
        Client savedClient = createClient(client);
        return savedClient.getId(); 
    }

	public Optional<Client> existeEmail(String email) {
		
		return clientRepository.findByEmail(email);
	}

	
}
