import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConnexionComponent } from './connexion/connexion.component';
import { InscriptionComponent } from './inscription/inscription.component';
import { InterfaceComponent } from './interface/interface.component';
import { NavbarComponent } from './navbar/navbar.component';
import { ListProduitsComponent } from './list-produits/list-produits.component';
import { PanierComponent } from './panier/panier.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ProduitComponent } from './produit/produit.component';

@NgModule({
  declarations: [
    AppComponent,
    ConnexionComponent,
    InscriptionComponent,
    InterfaceComponent,
    NavbarComponent,
    ListProduitsComponent,
    PanierComponent,
    ProduitComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
    ReactiveFormsModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
