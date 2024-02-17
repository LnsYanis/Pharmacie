import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConnexionService } from '../connexion.service';
@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  constructor(
    private router: Router,
    private connexionService: ConnexionService
  ) {}
  seDeconnecter() {
    this.connexionService.deconnecter().subscribe(
      (resultat) => {
        this.router.navigate(['']);
      },
      (erreur) => {
        // Gérer les erreurs si nécessaire
      }
    );
  }
}
