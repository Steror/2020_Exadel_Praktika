import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';

@Injectable(/*{
  providedIn: 'root'
}*/)
export class AppService {

  public authenticated = false;
  public options = {};
  public principal;
  public loginUrl = '//localhost:8080/login';
  public userUrl = '//localhost:8080/user';

  constructor(private http: HttpClient, private router: Router) { }

  authenticate(credentials, callback) {
    this.http.post<Observable<boolean>>(this.loginUrl, {
      userName: credentials.username,
      password: credentials.password
    }).subscribe(isValid => {
      if (isValid) {
        this.authenticated = true;
        sessionStorage.setItem('token', btoa(credentials.username + ':' + credentials.password));
        this.authorize();
      } else {
        alert('Authentication failed.');
      }
      return callback && callback();
    });
  }

  authorize() {
    if (sessionStorage.getItem('token') !== null) {
      this.createHeader();
      const headers: HttpHeaders = new HttpHeaders({
        Authorization: 'Basic ' + sessionStorage.getItem('token')
      });
      const options = { headers };
      this.http.post<Observable<any>>(this.userUrl, {}, options).
      subscribe(principal => {
          this.principal = principal;
          this.authenticated = true;
        },
        error => {
          console.log('Failed to authorize');
          this.router.navigateByUrl('/login');
        }
      );
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  createHeader() {
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: 'Basic ' + sessionStorage.getItem('token')
    });
    this.options = { headers };
  }
}
