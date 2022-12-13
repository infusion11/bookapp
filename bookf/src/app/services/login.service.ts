import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, map, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private restUrl = environment.restUrl;
  
  constructor(private http: HttpClient) { }

public login(usern: String, pass: String): Observable<any>{
  const httpOptions = {
    headers: new HttpHeaders({
      'Authorization': 'Basic ' + window.btoa(usern+':'+pass)
    })
  };
  return this.http.get<any>(`${this.restUrl}/bookapp/v1/login`, httpOptions)
}

}