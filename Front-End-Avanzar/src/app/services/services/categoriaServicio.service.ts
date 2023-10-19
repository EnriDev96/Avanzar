import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { CategoriaServicio } from 'app/modules/emprendedora/ecommerce copy/inventory/inventoryServicios.types';

@Injectable({
  providedIn: 'root'
})
export class CategoriaServicioService {

  private baseUrl = 'https://164.90.153.70:8080/api/categoriaServicio';

  constructor(private http: HttpClient) { }


  getCategoriaServicio(idCategoria: any): Observable<CategoriaServicio> {
    return this.http.get<CategoriaServicio>(`${this.baseUrl}/buscar/${idCategoria}`)
  }

}
