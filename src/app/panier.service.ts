import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PanierService {
  private apiUrl = 'http://localhost:8080/api/panier';

  constructor(private http: HttpClient) {}

  ajouterAuPanier(
    nom: string,
    quantite: number,
    prix: number,
    clientId: string
  ): Observable<any> {
    const data = {
      produit: nom,
      quantite: quantite,
      prix: prix,
      clientId: clientId,
    };

    return this.http.post<any>(`${this.apiUrl}/ajouter`, data);
  }
  decrementerQuantiteProduit(
    nomProduit: string,
    quantiteDecrementee: number
  ): Observable<void> {
    const url = `${this.apiUrl}/decrementer-quantite/${nomProduit}/${quantiteDecrementee}`;
    return this.http.put<void>(url, null);
  }
}
