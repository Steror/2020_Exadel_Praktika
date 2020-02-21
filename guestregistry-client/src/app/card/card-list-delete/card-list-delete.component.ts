import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {CardService} from "../card.service";
import {Card} from "../card";
// import {Card} from "../card";
// import {Card} from "../card";

@Component({
  selector: 'app-card-list-delete',
  templateUrl: './card-list-delete.component.html',
  styleUrls: ['./card-list-delete.component.css']
})
export class CardListDeleteComponent implements OnInit {
  cards:Array<Card>;
  // searchableId:any;

  constructor(private service:CardService,
              private router:Router) { }

  ngOnInit() {
    this.loadCards();
  }

  public deleteCard(cardId:any){
    let response = this.service.deleteCard(cardId);
    response.subscribe(()=> this.loadCards());
  }

  private loadCards() {
    let response = this.service.getCards();
    response.subscribe((data:Array<Card>)=>{
      // console.log(data/*["0"].id.toString()*/),
      this.cards=data
    });
  }

  public findCardById(cardId:any) {
    this.cards=[];
    let response = this.service.getCard(cardId)
    response.subscribe((data:Card)=> this.cards=[data]);
  }

  public goToPage(address) {
    this.router.navigate([address]).then(() => window.location.reload())
  }
}
