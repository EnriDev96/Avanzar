import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { CategoriaPublicacion } from '../models/categoria';

@Injectable({
  providedIn: 'root'
})
export class CategoriaPublicacionService {

  private baseUrl = 'https://164.90.153.70:8080/api/categoria'; // Cambia la URL a la de tu servidor

  constructor(private http: HttpClient) { }


  buscarCategoriaId(idCategoria: any): Observable<CategoriaPublicacion> {
    return this.http.get<CategoriaPublicacion>(`${this.baseUrl}/buscar/${idCategoria}`)
  }

}
