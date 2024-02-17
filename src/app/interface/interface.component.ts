import { Component, OnInit } from '@angular/core';
import { ProduitService } from '../produit.service';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';

@Component({
  selector: 'app-interface',
  templateUrl: './interface.component.html',
  styleUrls: ['./interface.component.css'],
})
export class InterfaceComponent implements OnInit {
  isAdmin: boolean = false;
  clientId: string = '';
  successMessage: string = '';
  errorMessage: string = '';
  productIdToDelete: string = '';

  constructor(
    private produitservice: ProduitService,
    private route: Router,
    private connexionService: ConnexionService
  ) {}

  redirigerVersAjoutProduit() {
    this.route.navigate(['/Produit']);
  }

  deleteProduct() {
    const productId: string | null = prompt(
      "Veuillez saisir l'ID du produit à supprimer:"
    );
    if (productId !== null && /^\d+$/.test(productId)) {
      this.productIdToDelete = productId;

      this.produitservice.deleteProduct(this.productIdToDelete).subscribe(
        () => {
          console.log('Produit supprimé avec succès');
          this.successMessage = 'Produit supprimé avec succès';
          this.errorMessage = ''; // Effacer le message d'erreur en cas de succès
          this.productIdToDelete = '';
        },
        (error) => {
          if (error.status === 404) {
            this.errorMessage = "Le produit n'existe pas";
            this.successMessage = '';
          } else {
            console.error('Erreur lors de la suppression du produit : ', error);
          }
        }
      );
    } else if (productId !== null) {
      this.errorMessage =
        'Veuillez saisir un ID valide composé uniquement de chiffres.';
      this.successMessage = '';
    } else {
      this.errorMessage = '';
      this.successMessage = '';
    }
  }

  ngOnInit() {
    this.clientId = this.connexionService.getClientId();
    this.connexionService.getRoleId(this.clientId).subscribe(
      (response: any) => {
        const role = response.role;

        if (role === 'Admin') {
          this.isAdmin = true;
          console.log("C'est un administrateur.");
        } else {
          this.isAdmin = false;
          console.log("C'est un client.");
        }
      },
      (error) => {
        console.error(
          'Erreur lors de la récupération du rôle du client : ',
          error
        );
      }
    );
  }
}
