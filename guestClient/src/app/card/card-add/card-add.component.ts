import { Component, OnInit } from '@angular/core';
import {CardServiceService} from "../card-service.service";
import {PreloadAllModules, Router} from "@angular/router";
import {Card} from "../card";
import {subscribeOn} from "rxjs/operators";
import DateTimeFormat = Intl.DateTimeFormat;

@Component({
  selector: 'app-card-add',
  templateUrl: './card-add.component.html',
  styleUrls: ['./card-add.component.css']
})
export class CardAddComponent implements OnInit {
  card:Card;
  public selectedMoments = [
    new Date(2000, 1, 12, 10, 30),
    new Date(2033, 2, 13, 20, 30)
  ]

  constructor(private service:CardServiceService,
              private router:Router) { }

  ngOnInit(): void {
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
