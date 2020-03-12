import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { Observable } from 'rxjs';
import {AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  public API = '//localhost:8080/api/location';


  constructor(private http: HttpClient, private appService: AppService) { }

  getAll(): Observable<any> {
    return this.http.get(this.API, this.appService.options);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString(), this.appService.options);
  }

  save(location: any): Observable<any> {
    let result: Observable<any>;
    if (location.id) {
      result = this.http.put(this.API + '/' + location.id, location, this.appService.options);
    } else {
      result = this.http.post(this.API, location, this.appService.options);
    }
    return result;
  }

  remove(id: any) {
    return this.http.delete(this.API + '/' + id.toString(), this.appService.options);
  }
}
