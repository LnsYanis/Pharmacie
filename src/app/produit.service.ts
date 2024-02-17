import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ProduitService {
  private urlBackend = 'http://localhost:8080/api/Produit';

  constructor(private http: HttpClient) {}

  getProduits(): Observable<any[]> {
    const url = `${this.urlBackend}/liste`;
    return this.http.get<any[]>(url);
  }

  deleteProduct(productId: string): Observable<any> {
    const url = `${this.urlBackend}/${productId}`;
    return this.http.delete(url).pipe(
      catchError((error) => {
        console.error('Erreur lors de la suppression du produit : ', error);
        return throwError(error);
      })
    );
  }

  decrementerQuantiteProduit(
    nomProduit: string,
    quantiteDecrementee: number
  ): Observable<void> {
    const url = `${this.urlBackend}/decrementer-quantite/${nomProduit}/${quantiteDecrementee}`;
    return this.http.put<void>(url, null);
  }
}
