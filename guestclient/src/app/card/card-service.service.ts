import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CardServiceService {

  constructor(private http:HttpClient) { }

  public addCard(card) {
    return this.http.post("http://localhost:8080/api/card", card);
  }

  public getCards(){
    return this.http.get("http://localhost:8080/api/card");
  }

  public getCard(id){
    return this.http.get("http://localhost:8080/api/card/"+id);
  }

  public updateCard(id, card) {
    return this.http.put("http://localhost:8080/api/card/"+id, card);
  }

  public deleteCard(id){
    return this.http.delete("http://localhost:8080/api/card/" + id);
    // return this.http.delete("http://localhost:8080/api/card",{id:id} );
  }
}
