import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  public API = 'http://localhost:8080/api/person';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.API);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString());
  }

  save(person: any): Observable<any> {
    let result: Observable<any>;
    if (person.id) {
      result = this.http.put(this.API + '/' + person.id, person);
    } else {
      result = this.http.post(this.API, person);
    }
    return result;
  }

  remove(id: any) {
    return this.http.delete(this.API + '/' + id.toString());
  }
}
