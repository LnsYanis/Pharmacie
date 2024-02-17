package com.Pharmacie.PH.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.PH.Model.Panier;
import com.Pharmacie.PH.Request.PanierRequest;
import com.Pharmacie.PH.Service.PanierService;

@RestController
@RequestMapping("/api/panier")
public class PanierController {

    private final PanierService panierService;

    @Autowired
    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterAuPanier(@RequestBody PanierRequest request) {
    	System.out.print(request.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            panierService.ajouterAuPanier(request);
            return ResponseEntity.ok("{\"message\": \"Ajout au panier réussi.\"}");
        } else {
            return ResponseEntity.status(401).body("{\"message\": \"Utilisateur non authentifié.\"}");
        }
    }
    @GetMapping("/commandes-client/{clientId}")
    public ResponseEntity<List<Panier>> getCommandesClient(@PathVariable String clientId) {
        System.out.println("Received clientId in controller: " + clientId);

        try {
            List<Panier> commandes = panierService.getCommandesClient(clientId);
            return ResponseEntity.ok(commandes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    @DeleteMapping("/supprimer-commande/{clientId}/{nomProduit}")
    public ResponseEntity<Map<String, String>> supprimerCommande(
            @PathVariable String clientId,
            @PathVariable String nomProduit) {
        try {
            panierService.supprimerCommande(clientId, nomProduit);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Commande supprimée avec succès.");
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Erreur lors de la suppression de la commande.");
            return ResponseEntity.status(500).body(errorMap);
        }
    }
    @DeleteMapping("/supprimer-panier/{clientId}")
    public ResponseEntity<Map<String, String>> supprimerPanier(@PathVariable String clientId) {
        try {
            panierService.supprimerPanier(clientId);
            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("message", "Panier supprimé avec succès.");

            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            e.printStackTrace();

           
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "Erreur lors de la suppression du panier.");

            return ResponseEntity.status(500).body(errorMap);
        }
    }
   

}




