import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../produit.service';
import { ConnexionService } from '../connexion.service';
import { PanierService } from '../panier.service';

@Component({
  selector: 'app-list-produits',
  templateUrl: './list-produits.component.html',
  styleUrls: ['./list-produits.component.css'],
})
export class ListProduitsComponent implements OnInit {
  produits: any[] = [];

  selectedProduit: any;
  quantiteSelectionnee: number = 1;
  messageQuantite: string | null = null;

  constructor(
    private produitService: ProduitService,
    private connexionService: ConnexionService,
    private panierService: PanierService
  ) {}

  ngOnInit() {
    this.produitService.getProduits().subscribe(
      (data) => {
        this.produits = data;
      },
      (erreur) => {
        console.error('Erreur lors de la récupération des produits:', erreur);
      }
    );
  }

  ajouterAuPanier(produit: any): void {
    this.selectedProduit = produit;
    this.quantiteSelectionnee = 1;
    this.messageQuantite = null;
  }

  validerAjoutAuPanier(): void {
    if (
      this.quantiteSelectionnee &&
      this.quantiteSelectionnee >= 0 &&
      this.quantiteSelectionnee <= this.selectedProduit.quantite
    ) {
      const clientId = this.connexionService.getClientId();
      const { nom, prix } = this.selectedProduit;

      this.panierService
        .ajouterAuPanier(nom, this.quantiteSelectionnee, prix, clientId)
        .subscribe(
          (data) => {
            console.log('Ajout au panier réussi :', data);
          },
          (erreur) => {
            console.error("Erreur lors de l'ajout au panier :", erreur);
          }
        );
    } else if (this.quantiteSelectionnee < 0) {
      this.messageQuantite = 'Veuillez sélectionner une quantité positive.';
    } else {
      this.messageQuantite =
        'La quantité sélectionnée est supérieure à la quantité en stock.';
    }
  }
}
