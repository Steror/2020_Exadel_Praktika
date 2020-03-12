import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  public API = '//localhost:8080/api/location';
  public options;


  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    this.createHeader();
    return this.http.get(this.API, this.options);
  }

  get(id: any) {
    this.createHeader();
    return this.http.get(this.API + '/' + id.toString(), this.options);
  }

  save(location: any): Observable<any> {
    this.createHeader();
    let result: Observable<any>;
    if (location.id) {
      result = this.http.put(this.API + '/' + location.id, location, this.options);
    } else {
      result = this.http.post(this.API, location, this.options);
    }
    return result;
  }

  remove(id: any) {
    this.createHeader();
    return this.http.delete(this.API + '/' + id.toString(), this.options);
  }

  authorize() {
    const url = 'http://localhost:8080/user';

    const headers: HttpHeaders = new HttpHeaders({
      Authorization: 'Basic ' + sessionStorage.getItem('token')
    });

    const options = { headers };
    this.http.post<Observable<any>>(url, {}, options).
    subscribe(principal => {
        console.log(principal['name']);
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
