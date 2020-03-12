import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable(/*{
  providedIn: 'root'
}*/)
export class AppService {

  public authenticated = false;
  public options = {};
  public principal;
  public loginUrl = '//localhost:8080/login';
  public userUrl = '//localhost:8080/user';

  constructor(private http: HttpClient) { }

  authenticate(credentials, callback) {
    this.http.post<Observable<boolean>>(this.loginUrl, {
      userName: credentials.username,
      password: credentials.password
    }).subscribe(isValid => {
      if (isValid) {
        this.authenticated = true;
        sessionStorage.setItem('token', btoa(credentials.username + ':' + credentials.password));
        this.createHeader();
      } else {
        alert('Authentication failed.');
      }
      return callback && callback();
    });
  }

  authorize() {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: 'Basic ' + sessionStorage.getItem('token')
    });
    const options = { headers };
    this.http.post<Observable<any>>(this.userUrl, {}, options).
    subscribe(principal => {
        this.principal = principal;
      },
      error => {
        if (error.status === 401) {
          alert('Unauthorized');
        }
      }
    );
  }

  createHeader() {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: 'Basic ' + sessionStorage.getItem('token')
    });
    this.options = { headers };
  }
}
