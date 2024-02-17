import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ConnexionService } from '../connexion.service';
import { Location } from '@angular/common';
import { Facture } from '../facture.model';
import { Router } from '@angular/router';
import { ProduitService } from '../produit.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-panier',
  templateUrl: './panier.component.html',
  styleUrls: ['./panier.component.css'],
})
export class PanierComponent implements OnInit {
  commandes: any[] = [];
  panierVide: boolean = false;
  totalForm: FormGroup;

  constructor(
    private router: Router,
    private httpClient: HttpClient,
    private connexionService: ConnexionService,
    private formBuilder: FormBuilder,
    private location: Location,
    private produitService: ProduitService
  ) {
    this.totalForm = this.formBuilder.group({
      prixTotal: [0, Validators.required],
      quantiteTotale: [0, Validators.required],
    });
  }
  facture: Facture | null = null;
  ngOnInit(): void {
    this.getCommandesClient();
    this.initTotalForm();
  }

  getCommandesClient(): void {
    const clientId = this.connexionService.getClientId();
    const url = `http://localhost:8080/api/panier/commandes-client/${clientId}`;

    this.httpClient.get<any[]>(url).subscribe(
      (response) => {
        this.commandes = response.map((commande) => ({
          ...commande,
          editMode: false,
          commandeForm: this.initCommandeForm(commande),
        }));
        if (this.commandes.length === 0) {
          this.panierVide = true;
        } else {
          this.panierVide = false;
        }
        this.updateTotalForm();
      },
      (error) => {
        console.error('Erreur lors de la récupération des commandes : ', error);

        if (error.error instanceof ErrorEvent) {
          console.error('Erreur côté client : ', error.error.message);
        } else {
          console.error('Erreur côté serveur : ', error.status, error.error);
        }
      }
    );
  }

  initCommandeForm(commande: any): FormGroup {
    return this.formBuilder.group({
      nom: [commande.nom, Validators.required],
      prix: [commande.prix, Validators.required],
      quantite: [commande.quantite, Validators.required],
      prixTotalDuProduit: [commande.prixTotalDuProduit, Validators.required],
    });
  }

  initTotalForm(): void {
    this.totalForm = this.formBuilder.group({
      prixTotal: [0, Validators.required],
      quantiteTotale: [0, Validators.required],
    });
  }

  updateTotalForm(): void {
    const prixTotal = this.commandes.reduce(
      (total, commande) => total + commande.prixTotalDuProduit,
      0
    );
    const quantiteTotale = this.commandes.reduce(
      (total, commande) => total + commande.quantite,
      0
    );

    this.totalForm.patchValue({
      prixTotal: prixTotal,
      quantiteTotale: quantiteTotale,
    });
  }

  supprimerCommande(commande: any): void {
    const clientId = this.connexionService.getClientId();
    const nomProduit = commande.nom;

    const url = `http://localhost:8080/api/panier/supprimer-commande/${clientId}/${nomProduit}`;

    this.httpClient.delete(url).subscribe(
      () => {
        this.commandes = this.commandes.filter((c) => c !== commande);
        this.commandes = this.commandes.filter((c) => c !== commande);
        this.updateTotalForm();
      },
      (error) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }

  validateQuantity(commande: any, event: Event): void {}

  payerPanier(): void {
    const clientId = this.connexionService.getClientId();
    const url3 = `http://localhost:8080/api/facture/Ajouter-Facture/${clientId}`;

    const payload = {
      prixTotal: this.totalForm.value.prixTotal,
      quantiteTotale: this.totalForm.value.quantiteTotale,
    };

    this.httpClient.post(url3, payload).subscribe(
      (response: any) => {
        const factureId = response.factureId;

        const decrementObservables = this.commandes.map(
          (commande) =>
            this.produitService.decrementerQuantiteProduit(
              commande.nom,
              commande.quantite
            ),
          console.log('ça marche !!')
        );

        forkJoin(decrementObservables).subscribe(
          () => {
            this.supprimerPanier();
            console.log('Panier supprimé et facture créée avec succès!');

            const urlFacture = `http://localhost:8080/api/facture/recuperer/${factureId}`;

            this.httpClient.get(urlFacture).subscribe(
              (factureData: any) => {
                this.facture = factureData;
                console.log('Récuperation de la facture !!', this.facture);
              },
              (error) => {
                console.error(
                  'Erreur lors de la récupération des données de la facture : ',
                  error
                );
              }
            );
          },
          (error) => {
            console.error(
              'Erreur lors de la décrémentation de la quantité des produits : ',
              error
            );
          }
        );
      },
      (error) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }

  supprimerPanier(): void {
    const clientId = this.connexionService.getClientId();
    const url2 = `http://localhost:8080/api/panier/supprimer-panier/${clientId}`;
    this.httpClient.delete(url2).subscribe(
      () => {
        console.log('Panier supprimée avec succès !');
        this.commandes = [];
        this.updateTotalForm();
      },
      (error) => {
        console.error('Erreur lors de la suppression de la commande : ', error);
      }
    );
  }
  retourAccueil() {
    this.router.navigate(['/interface']);
  }
}
