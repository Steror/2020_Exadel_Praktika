import { Component, OnInit } from '@angular/core';
import {CardServiceService} from "../card-service.service";
import {Router} from "@angular/router";
import {Card} from "../card";

@Component({
  selector: 'app-card-add',
  templateUrl: './card-add.component.html',
  styleUrls: ['./card-add.component.css']
})
export class CardAddComponent implements OnInit {
  card:Card;

  constructor(private service:CardServiceService,
              private router:Router) { }

  ngOnInit(): void {
    this.card = new Card();
  }

  public addCard() {
    this.service.addCard(this.card).subscribe();
    console.log(this.card)
    this.gotoPage('/card/list');
  }

  public gotoPage(address) {
    // console.log("card-list trying to redirect to " + address);
    this.router.navigate([address]).catch();
  }

}
