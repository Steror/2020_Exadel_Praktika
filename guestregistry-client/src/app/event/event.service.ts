import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  public API = '//localhost:8080/api/event';

  constructor(private http: HttpClient, private appService: AppService) { }

  getAll(): Observable<any> {
    return this.http.get(this.API, this.appService.options);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString(), this.appService.options);
  }

  save(event: any): Observable<any> {
    let result: Observable<any>;
    if (event.id) {
      result = this.http.put(this.API + '/' + event.id, event, this.appService.options);
    } else {
      result = this.http.post(this.API, event, this.appService.options);
    }
    return result;
  }

  remove(id: any) {
    console.log(typeof id);
    return this.http.delete(this.API + '/' + id.toString(), this.appService.options);
  }
}
