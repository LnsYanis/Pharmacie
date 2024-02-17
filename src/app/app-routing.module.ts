import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ConnexionComponent } from './connexion/connexion.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { InterfaceComponent } from './interface/interface.component';
import { PanierComponent } from './panier/panier.component';
import { ProduitComponent } from './produit/produit.component';

const routes: Routes = [
  { path: '', component: ConnexionComponent },
  { path: 'inscription', component: InscriptionComponent },
  { path: 'interface', component: InterfaceComponent },
  { path: 'panier', component: PanierComponent },
  { path: 'Produit', component: ProduitComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
