import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AppService} from '../app.service';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  public API = 'http://localhost:8080/api/card';

  constructor(private http: HttpClient, private appService: AppService) { }


  public addCard(card) {
    return this.http.post(this.API, card, this.appService.options);
  }

  public getCards() {
    return this.http.get(this.API, this.appService.options);
  }

  public getCard(id) {
    return this.http.get(this.API + '/' + id, this.appService.options);
  }

  public updateCard(id, card) {
    return this.http.put(this.API + '/' + id, card, this.appService.options);
  }

  public deleteCard(id) {
    return this.http.delete('http://localhost:8080/api/card/' + id, this.appService.options);
    // return this.http.delete("http://localhost:8080/api/card",{id:id} );
  }
}
