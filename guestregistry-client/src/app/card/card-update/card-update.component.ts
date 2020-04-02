import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CardService} from "../card.service";
import {Card} from "../card";
import {LocationService} from "../../location/location.service";

@Component({
  selector: 'app-card-update',
  templateUrl: './card-update.component.html',
  styleUrls: ['./card-update.component.css']
})
export class CardUpdateComponent implements OnInit {
  id:number;
  card:Card;
  locations:any;

  public selectedMoments = [new Date(), new Date()];
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: CardService,
    private locationService: LocationService
  )
  // private typos: CardTypes)
  { }

  ngOnInit() {
    let response = this.locationService.getAll();
    response.subscribe((data)=> this.locations = data);
    this.card = new Card();
    this.id = this.route.snapshot.params['id'];
    // console.log(" Fetching" + this.id);
    this.service.getCard(this.id)
      .subscribe((data:Card)=> this.card = data);
    this.selectedMoments.push(this.card.manufactured);
    this.selectedMoments.push(this.card.validUntil);
  }

  public updateCard() {
    this.card.manufactured = this.selectedMoments[0];
    this.card.validUntil = this.selectedMoments[1];
    this.service.updateCard(this.id, this.card).subscribe();
    this.gotoPage('/card/list')
  }

  public gotoPage(address) {
    this.router.navigate([address]).catch();
  }

}
