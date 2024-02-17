import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';

@Component({
  selector: 'app-connexion',
  templateUrl: './connexion.component.html',
  styleUrls: ['./connexion.component.css'],
})
export class ConnexionComponent {
  utilisateur = { email: '', mdp: '' };
  messageErreur: string;

  constructor(
    private router: Router,
    private connexionService: ConnexionService
  ) {
    this.messageErreur = '';
  }

  seConnecter() {
    this.messageErreur = ''; // Réinitialise le message d'erreur à chaque tentative

    this.connexionService
      .verifierIdentifiants(this.utilisateur.email, this.utilisateur.mdp)
      .subscribe(
        (resultat) => {
          console.log('Résultat de la connexion:', resultat);
          const clientId = resultat.clientId;
          this.connexionService.setClientId(clientId);
          console.log('ID du client:', clientId);
          this.router.navigate(['/interface']);
        },
        (erreur) => {
          if (erreur.status === 401) {
            this.messageErreur = 'Identifiants incorrects. Veuillez réessayer.';
            this.utilisateur.email = '';
            this.utilisateur.mdp = '';
          } else {
            console.error('Erreur lors de la connexion:', erreur);
          }
        }
      );
  }
}
