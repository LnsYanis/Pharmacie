import { client } from './client.Model';
export interface Facture {
  id: number;
  valeur: number;
  quantites: number;
  client: client;
}
