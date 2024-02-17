package com.Pharmacie.PH.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Pharmacie.PH.Model.Produit;
import com.Pharmacie.PH.Service.ProduitService;

@RestController
@RequestMapping("/api/Produit")
public class ProduitController {

    private final ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/liste")
    public ResponseEntity<List<Produit>> getListeProduits() {
        List<Produit> produits = produitService.getListeProduits();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return ResponseEntity.ok().headers(headers).body(produits);
    }
    @PutMapping("/decrementer-quantite/{nomProduit}/{quantiteDecrementee}")
    public ResponseEntity<Void> decrementerQuantiteProduit(
            @PathVariable String nomProduit,
            @PathVariable Long quantiteDecrementee) {
    	produitService.decrementerQuantiteProduit(nomProduit, quantiteDecrementee);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return ResponseEntity.noContent().headers(headers).build();
    }
    @PostMapping("/AjoutProduit")  
    public ResponseEntity<String> ajouterProduit(@RequestBody Produit produit) {
        try {
            produitService.ajouterProduit(produit);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return ResponseEntity.ok().headers(headers).body("{\"message\":\"Produit ajouté avec succès\"}");
        } catch (RuntimeException e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
            return ResponseEntity.status(409).headers(headers).body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    @DeleteMapping("/{produitId}")
    public ResponseEntity<?> supprimerProduit(@PathVariable Long produitId) {
        if (!produitService.existsById(produitId)) {
            return new ResponseEntity<>("{\"message\": \"Produit introuvable.\"}", HttpStatus.NOT_FOUND);
        }
        produitService.supprimerProduit(produitId);
        return new ResponseEntity<>("{\"message\": \"Produit supprimé avec succès.\"}", HttpStatus.OK);
    }

}