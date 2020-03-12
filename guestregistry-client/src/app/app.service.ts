import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable(/*{
  providedIn: 'root'
}*/)
export class AppService {

  authenticated = false;
  public API = '//localhost:8080/login';

  constructor(private http: HttpClient) { }

  // authenticate(credentials, callback) {
  //   const headers = new HttpHeaders(credentials ? {
  //     authorization : 'Basic ' + btoa(credentials.username + ':' + credentials.password)
  //   } : {});
  //   this.http.post<Observable<boolean>>(this.API, {headers}).subscribe(response => {
  //     /* tslint:disable:no-string-literal */
  //     if (response['name']) { // tslint doesn't like string literals
  //       this.authenticated = true;
  //     } else {
  //       this.authenticated = false;
  //     }
  //     /* tslint:disable:no-string-literal */
  //     return callback && callback();
  //   });
  // }
  authenticate(credentials, callback) {
    this.http.post<Observable<boolean>>(this.API, {
      userName: credentials.username,
      password: credentials.password
    }).subscribe(isValid => {
      if (isValid) {
        sessionStorage.setItem('token', btoa(credentials.username + ':' + credentials.password));
      } else {
        alert('Authentication failed.');
      }
      return callback && callback();
    });
  }
}
