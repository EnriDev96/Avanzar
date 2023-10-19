import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EmailDto } from '../models/emailDto';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  url: string = 'https://164.90.153.70:8080/api/email';
  constructor(private http: HttpClient) { }


  sendCodeVer(e: EmailDto): Observable<EmailDto> {
    const url = `${this.url}/sendCodeVerification`;
    return this.http.post<EmailDto>(url,e);
  }

  sendresetCode(e:EmailDto):Observable<boolean>{
    return this.http.put<boolean>(`${this.url}/resetPass/`, e);
  }

}
