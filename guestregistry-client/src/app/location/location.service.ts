import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LocationService {
  public API = '//localhost:8080/api/location';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.API);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString());
  }

  save(location: any): Observable<any> {
    let result: Observable<any>;
    if (location.id) {
      console.log(location);
      result = this.http.put(this.API + '/' + location.id, location);
    } else {
      result = this.http.post(this.API, location);
    }
    return result;
  }

  remove(id: any) {
    console.log(typeof id);
    return this.http.delete(this.API + '/' + id.toString());
  }
}
