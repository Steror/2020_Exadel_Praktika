import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CardServiceService} from "../card-service.service";
import {Card} from "../card";
import DateTimeFormat = Intl.DateTimeFormat;

@Component({
  selector: 'app-card-update',
  templateUrl: './card-update.component.html',
  styleUrls: ['./card-update.component.css']
})
export class CardUpdateComponent implements OnInit {
  id:number;
  card:Card;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: CardServiceService)
  { }

  ngOnInit() {
    this.card = new Card();
    // this.card = null;
    this.id = this.route.snapshot.params['id'];
    // console.log(" Fetching" + this.id);
    this.service.getCard(this.id)
      .subscribe((data:Card)=> this.card = data);
  }

  public updateCard() {
    this.service.updateCard(this.id, this.card).subscribe();
    this.gotoPage('/card/list')
  }

  public gotoPage(address) {
    this.router.navigate([address]).catch();
  }

}
