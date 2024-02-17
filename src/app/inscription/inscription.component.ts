import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';

@Component({
  selector: 'app-inscription',
  templateUrl: './inscription.component.html',
  styleUrls: ['./inscription.component.css'],
})
export class InscriptionComponent {
  utilisateur = { nom: '', email: '', prenom: '', mdp: '', role: '' };
  messageErreur: string = '';

  constructor(
    private http: HttpClient,
    private router: Router,
    private connexionService: ConnexionService
  ) {}

  sInscrire() {
    this.messageErreur = '';
    if (
      this.utilisateur.nom &&
      this.utilisateur.email &&
      this.utilisateur.prenom &&
      this.utilisateur.mdp &&
      this.utilisateur.role
    ) {
      this.connexionService.sInscrire(this.utilisateur).subscribe(
        (response) => {
          console.log('Inscription réussie', response);

          this.connexionService.setClientId(response.clientId);
          this.router.navigate(['/interface']);
        },
        (error) => {
          console.error("Échec de l'inscription", error);

          if (error.status === 409) {
            this.messageErreur = "L'utilisateur existe déjà.";
            this.utilisateur.nom = '';
            this.utilisateur.mdp = '';
            this.utilisateur.email = '';
            this.utilisateur.prenom = '';
          } else {
            // Gérer d'autres erreurs si nécessaire
          }
        }
      );
    } else {
      this.messageErreur = 'Veuillez remplir tous les champs.';
    }
  }
}
