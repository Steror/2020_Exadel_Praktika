import { Component, OnInit } from '@angular/core';
import {CardServiceService} from "../card-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-card-add',
  templateUrl: './card-add.component.html',
  styleUrls: ['./card-add.component.css']
})
export class CardAddComponent implements OnInit {

  constructor(private service:CardServiceService,
              private router:Router) { }

  ngOnInit(): void {
  }

  public goToPage(address) {
    // console.log("card-list trying to redirect to " + address);
    this.router.navigate([address]).catch();
  }
}
