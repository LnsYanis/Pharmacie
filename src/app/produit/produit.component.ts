import { Component } from '@angular/core';
import { ConnexionService } from '../connexion.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-produit',
  templateUrl: './produit.component.html',
  styleUrls: ['./produit.component.css'],
})
export class ProduitComponent {
  constructor(
    private connexionService: ConnexionService,
    private http: HttpClient
  ) {}

  nomProduit: string | null = null;
  quantite: number | null = null;
  prix: number | null = null;
  clientId: string = this.connexionService.getClientId();
  erreurAjoutProduit: string | null = null;

  ajouterProduit() {
    if (
      this.nomProduit === null ||
      this.quantite === null ||
      this.prix === null
    ) {
      console.error('Veuillez remplir tous les champs du formulaire.');
      return;
    }
    const produit = {
      nom: this.nomProduit,
      quantite: this.quantite,
      prix: this.prix,
      clientId: this.clientId,
    };
    this.http
      .post('http://localhost:8080/api/Produit/AjoutProduit', produit)
      .subscribe(
        (response) => {
          console.log('Produit ajouté avec succès', response);
          this.nomProduit = null;
          this.quantite = null;
          this.prix = null;
          this.erreurAjoutProduit = null;
        },
        (error) => {
          if (error.status === 401) {
            console.error(
              "Erreur 401 Unauthorized: Problème d'authentification."
            );
          } else if (error.status === 409) {
            this.erreurAjoutProduit =
              'Le produit existe déjà. Veuillez changer le nom.';
            this.nomProduit = null;
            this.quantite = null;
            this.prix = null;
          } else {
            console.error('Erreur inattendue', error);
          }
        }
      );
  }
}
