import { Component, OnInit } from '@angular/core';
import {Card} from "../card";

@Component({
  selector: 'app-type-selectlist',
  templateUrl: './type-selectlist.component.html',
  styleUrls: ['./type-selectlist.component.css']
})
export class TypeSelectlistComponent implements OnInit {

  card:Card;
  // types = Array<CardType>
  types = Array<any>();
  selectedType:any;
  constructor() { }

  ngOnInit(): void {
    this.types.push(new Colour(-1, 'Please select'));
    this.types.push(new Colour(1, 'Green'));
    this.types.push(new Colour(2, 'Pink'));

    this.card = new Card();
    this.card.ctype = this.colours[1];
  }

}
