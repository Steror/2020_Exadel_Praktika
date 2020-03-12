import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WorkerService {

  public API = '//localhost:8080/api/worker';

  constructor(private http: HttpClient) { }

  getAll(): Observable<any> {
    return this.http.get(this.API);
  }

  get(id: any) {
    return this.http.get(this.API + '/' + id.toString());
  }

  save(worker: any): Observable<any> {
    let result: Observable<any>;
    if (worker.workerId) {
      result = this.http.put(this.API + '/' + worker.workerId, worker);
    } else {
      result = this.http.post(this.API, worker);
    }
    return result;
  }

  remove(id: any) {
    // console.log(typeof id);
    return this.http.delete(this.API + '/' + id.toString());
  }
}
