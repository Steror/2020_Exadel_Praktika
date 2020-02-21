import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CardService {

  public API = 'http://localhost:8080/api/card';

  constructor(private http:HttpClient) { }


  public addCard(card) {
    return this.http.post(this.API, card);
  }

  public getCards(){
    return this.http.get(this.API);
  }

  public getCard(id){
    return this.http.get(this.API + "/" + id);
  }

  public updateCard(id, card) {
    return this.http.put(this.API + "/" +id, card);
  }

  public deleteCard(id){
    return this.http.delete("http://localhost:8080/api/card/" + id);
    // return this.http.delete("http://localhost:8080/api/card",{id:id} );
  }
}
