import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  public API = '//localhost:8080/api/event';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.API);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString());
  }

  save(event: any): Observable<any> {
    let result: Observable<any>;
    if (event.id) {
      result = this.http.put(this.API + '/' + event.id, event);
    } else {
      result = this.http.post(this.API, event);
    }
    return result;
  }

  remove(id: any) {
    console.log(typeof id);
    return this.http.delete(this.API + '/' + id.toString());
  }
}
