import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ConnexionService {
  private clientId: string = '';
  private urlBackend = 'http://localhost:8080/api/connexion';

  constructor(private http: HttpClient) {}

  setClientId(clientId: string) {
    this.clientId = clientId;
  }

  getClientId(): string {
    return this.clientId;
  }
  sInscrire(utilisateur: any): Observable<any> {
    return this.http.post<any>(
      'http://localhost:8080/api/inscription',
      utilisateur
    );
  }

  verifierIdentifiants(email: string, mdp: string): Observable<any> {
    const body = { email, mdp };
    return this.http.post<any>(this.urlBackend, body);
  }

  deconnecter(): Observable<any> {
    const url = `${this.urlBackend}/deconnexion`;
    return this.http.post(url, {});
  }
  getRoleId(clientId: string): Observable<string> {
    const url = `http://localhost:8080/api/Role/${clientId}`;
    return this.http.get<string>(url);
  }
  getDetailsClient(email: string): Observable<any> {
    return this.http.get<any>(`${this.urlBackend}/client/details/${email}`);
  }
}
