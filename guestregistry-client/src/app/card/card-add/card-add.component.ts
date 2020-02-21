import { Component, OnInit } from '@angular/core';
import {CardService} from "../card.service";
import {Card} from "../card";
import {Router} from "@angular/router";
import {LocationService} from "../../location/location.service";

@Component({
  selector: 'app-card-add',
  templateUrl: './card-add.component.html',
  styleUrls: ['./card-add.component.css']
})
export class CardAddComponent implements OnInit {
  card:Card;
  locations:Array<any>;

  public selectedMoments = [
    new Date(2000, 1, 12, 10, 30),
    new Date(2033, 2, 13, 20, 30)
  ]

  constructor(private service:CardService,
              private locationService:LocationService,
              private router:Router) { }

  ngOnInit(): void {
    let response = this.locationService.getAll();
    response.subscribe((data:Array<any>)=>{
      this.locations=data;
    });
    this.card = new Card();
  }

  public addCard() {
    this.card.manufactured = this.selectedMoments[0];
    this.card.validUntil = this.selectedMoments[1];
    console.log(this.card.manufactured);
    console.log(this.card.validUntil);
    this.service.addCard(this.card).subscribe();
    console.log(this.card)
    this.gotoPage('/card/list');
  }

  public gotoPage(address) {
    // console.log("card-list trying to redirect to " + address);
    this.router.navigate([address]).then(() => window.location.reload())
  }

}
